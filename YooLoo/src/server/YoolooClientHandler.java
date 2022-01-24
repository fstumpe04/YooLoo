// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    130 | change ServerMessageType.SERVERMESSAGE_RESULT_SET to SERVERMESSAGE_RESULT_SET200107| cic |    130 | change ServerMessageType.SERVERMESSAGE_RESULT_SET to SERVERMESSAGE_RESULT_SET
//  V0.106  |      | cic |        | change empfangeVomClient(this.ois) to empfangeVomClient()


package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import client.YoolooClient.ClientState;
import common.LoginMessage;
import common.YoolooKarte;
import common.YoolooKartenspiel;
import common.YoolooSpieler;
import common.YoolooStich;
import messages.ClientMessage;
import messages.ServerMessage;
import messages.ServerMessage.ServerMessageResult;
import messages.ServerMessage.ServerMessageType;

public class YoolooClientHandler extends Thread {

	private final static int delay = 100;

	private YoolooServer myServer;

	private SocketAddress socketAddress = null;
	private Socket clientSocket;

	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private ServerState state;
	private YoolooSession session;
	private YoolooSpieler meinSpieler = null;
	private int clientHandlerId;

	public YoolooClientHandler(YoolooServer yoolooServer, Socket clientSocket) {
		this.myServer = yoolooServer;
		myServer.toString();
		this.clientSocket = clientSocket;
		this.state = ServerState.ServerState_NULL;
	}

	/**
	 * ClientHandler / Server Sessionstatusdefinition
	 */
	public enum ServerState {
		ServerState_NULL, // Server laeuft noch nicht
		ServerState_CONNECT, // Verbindung mit Client aufbauen
		ServerState_LOGIN, // noch nicht genutzt Anmeldung eines registrierten Users
		ServerState_REGISTER, // Registrieren eines Spielers
		ServerState_MANAGE_SESSION, // noch nicht genutzt Spielkoordination fuer komplexere Modi
		ServerState_PLAY_SESSION, // Einfache Runde ausspielen
		ServerState_DISCONNECT, // Session beendet ausgespielet Resourcen werden freigegeben
		ServerState_DISCONNECTED // Session terminiert
	};

	/**
	 * Serverseitige Steuerung des Clients
	 */
	@Override
	public void run() {
		try {
			state = ServerState.ServerState_CONNECT; // Verbindung zum Client aufbauen
			verbindeZumClient();

			state = ServerState.ServerState_REGISTER; // Abfragen der Spieler LoginMessage
			sendeKommando(ServerMessageType.SERVERMESSAGE_SENDLOGIN, ClientState.CLIENTSTATE_LOGIN, null);

			Object antwortObject = null;
			while (this.state != ServerState.ServerState_DISCONNECTED) {
				// Empfange Spieler als Antwort vom Client
				antwortObject = empfangeVomClient();
				if (antwortObject instanceof ClientMessage) {
					ClientMessage message = (ClientMessage) antwortObject;
					System.out.println("[ClientHandler" + clientHandlerId + "] Nachricht Vom Client: " + message);
				}
				switch (state) {
				case ServerState_REGISTER:
					// Neuer YoolooSpieler in Runde registrieren
					if (antwortObject instanceof LoginMessage) {
						LoginMessage newLogin = (LoginMessage) antwortObject;
						// TODO GameMode des Logins wird noch nicht ausgewertet
						meinSpieler = new YoolooSpieler(newLogin.getSpielerName(), YoolooKartenspiel.maxKartenWert);
						meinSpieler.setClientHandlerId(clientHandlerId);
						registriereSpielerInSession(meinSpieler);
						oos.writeObject(meinSpieler);
						sendeKommando(ServerMessageType.SERVERMESSAGE_SORT_CARD_SET, ClientState.CLIENTSTATE_SORT_CARDS,
								null);
						this.state = ServerState.ServerState_PLAY_SESSION;
						break;
					}
				case ServerState_PLAY_SESSION:
					switch (session.getGamemode()) {
					case GAMEMODE_SINGLE_GAME:
						// Triggersequenz zur Abfrage der einzelnen Karten des Spielers
						for (int stichNummer = 0; stichNummer < YoolooKartenspiel.maxKartenWert; stichNummer++) {
							sendeKommando(ServerMessageType.SERVERMESSAGE_SEND_CARD,
									ClientState.CLIENTSTATE_PLAY_SINGLE_GAME, null, stichNummer);
							// Neue YoolooKarte in Session ausspielen und Stich abfragen
							YoolooKarte neueKarte = (YoolooKarte) empfangeVomClient();
							System.out.println("[ClientHandler" + clientHandlerId + "] Karte empfangen:" + neueKarte);
							YoolooStich currentstich = spieleKarte(stichNummer, neueKarte);
							// Punkte fuer gespielten Stich ermitteln
							if (currentstich.getSpielerNummer() == clientHandlerId) {
								meinSpieler.erhaeltPunkte(stichNummer + 1);
							}
							System.out.println("[ClientHandler" + clientHandlerId + "] Stich " + stichNummer
									+ " wird gesendet: " + currentstich.toString());
							// Stich an Client uebermitteln
							oos.writeObject(currentstich);
						}
						this.state = ServerState.ServerState_DISCONNECT;
						break;
					default:
						System.out.println("[ClientHandler" + clientHandlerId + "] GameMode nicht implementiert");
						this.state = ServerState.ServerState_DISCONNECT;
						break;
					}
				case ServerState_DISCONNECT:
				// todo cic
				
            sendeKommando(ServerMessageType.SERVERMESSAGE_CHANGE_STATE, ClientState.CLIENTSTATE_DISCONNECTED,  null);
//					sendeKommando(ServerMessageType.SERVERMESSAGE_RESULT_SET, ClientState.CLIENTSTATE_DISCONNECTED,	null);
					oos.writeObject(session.getErgebnis());
					this.state = ServerState.ServerState_DISCONNECTED;
					break;
				default:
					System.out.println("Undefinierter Serverstatus - tue mal nichts!");
				}
			}
		} catch (EOFException e) {
			System.err.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			System.out.println("[ClientHandler" + clientHandlerId + "] Verbindung zu " + socketAddress + " beendet");
		}

	}

	private void sendeKommando(ServerMessageType serverMessageType, ClientState clientState,
			ServerMessageResult serverMessageResult, int paramInt) throws IOException {
		ServerMessage kommandoMessage = new ServerMessage(serverMessageType, clientState, serverMessageResult,
				paramInt);
		System.out.println("[ClientHandler" + clientHandlerId + "] Sende Kommando: " + kommandoMessage.toString());
		oos.writeObject(kommandoMessage);
	}

	private void sendeKommando(ServerMessageType serverMessageType, ClientState clientState,
			ServerMessageResult serverMessageResult) throws IOException {
		ServerMessage kommandoMessage = new ServerMessage(serverMessageType, clientState, serverMessageResult);
		System.out.println("[ClientHandler" + clientHandlerId + "] Sende Kommando: " + kommandoMessage.toString());
		oos.writeObject(kommandoMessage);
	}

	private void verbindeZumClient() throws IOException {
		oos = new ObjectOutputStream(clientSocket.getOutputStream());
		ois = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("[ClientHandler  " + clientHandlerId + "] Starte ClientHandler fuer: "
				+ clientSocket.getInetAddress() + ":->" + clientSocket.getPort());
		socketAddress = clientSocket.getRemoteSocketAddress();
		System.out.println("[ClientHandler" + clientHandlerId + "] Verbindung zu " + socketAddress + " hergestellt");
		oos.flush();
	}

	private Object empfangeVomClient() {
		Object antwortObject;
		try {
			antwortObject = ois.readObject();
			return antwortObject;
		} catch (EOFException eofe) {
			eofe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void registriereSpielerInSession(YoolooSpieler meinSpieler) {
		System.out
				.println("[ClientHandler" + clientHandlerId + "] registriereSpielerInSession " + meinSpieler.getName());
		session.getAktuellesSpiel().spielerRegistrieren(meinSpieler);
	}

	/**
	 * Methode spielt eine Karte des Client in der Session aus und wartet auf die
	 * Karten aller anderen Mitspieler. Dann wird das Ergebnis in Form eines Stichs
	 * an den Client zurueck zu geben
	 * 
	 * @param stichNummer
	 * @param empfangeneKarte
	 * @return
	 */
	private YoolooStich spieleKarte(int stichNummer, YoolooKarte empfangeneKarte) {
		YoolooStich aktuellerStich = null;
		System.out.println("[ClientHandler" + clientHandlerId + "] spiele Stich Nr: " + stichNummer
				+ " KarteKarte empfangen: " + empfangeneKarte.toString());
		session.spieleKarteAus(clientHandlerId, stichNummer, empfangeneKarte);
		// ausgabeSpielplan(); // Fuer Debuginformationen sinnvoll
		while (aktuellerStich == null) {
			try {
				System.out.println("[ClientHandler" + clientHandlerId + "] warte " + delay + " ms ");
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			aktuellerStich = session.stichFuerRundeAuswerten(stichNummer);
		}
		return aktuellerStich;
	}

	public void setHandlerID(int clientHandlerId) {
		System.out.println("[ClientHandler" + clientHandlerId + "] clientHandlerId " + clientHandlerId);
		this.clientHandlerId = clientHandlerId;

	}

	public void ausgabeSpielplan() {
		System.out.println("Aktueller Spielplan");
		for (int i = 0; i < session.getSpielplan().length; i++) {
			for (int j = 0; j < session.getSpielplan()[i].length; j++) {
				System.out.println("[ClientHandler" + clientHandlerId + "][i]:" + i + " [j]:" + j + " Karte: "
						+ session.getSpielplan()[i][j]);
			}
		}
	}

	/**
	 * Gemeinsamer Datenbereich fuer den Austausch zwischen den ClientHandlern.
	 * Dieser wird im jedem Clienthandler der Session verankert. Schreibender
	 * Zugriff in dieses Object muss threadsicher synchronisiert werden!
	 * 
	 * @param session
	 */
	public void joinSession(YoolooSession session) {
		System.out.println("[ClientHandler" + clientHandlerId + "] joinSession " + session.toString());
		this.session = session;

	}

}
