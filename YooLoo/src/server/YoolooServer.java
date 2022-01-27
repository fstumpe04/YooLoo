// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change 

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.YoolooKartenspiel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class YoolooServer {

	// Server Standardwerte koennen ueber zweite Konstruktor modifiziert werden!
	private int port = 44137;
	private int spielerProRunde = 8; // min 1, max Anzahl definierte Farben in Enum YoolooKartenSpiel.KartenFarbe)
	private GameMode serverGameMode = GameMode.GAMEMODE_SINGLE_GAME;

	public GameMode getServerGameMode() {
		return serverGameMode;
	}

	public void setServerGameMode(GameMode serverGameMode) {
		this.serverGameMode = serverGameMode;
	}

	private ServerSocket serverSocket = null;
	private boolean serverAktiv = true;

	// private ArrayList<Thread> spielerThreads;
	private ArrayList<YoolooClientHandler> clientHandlerList;
        
        

        private int zuschauer = 0;
        private int spieler = 0;
	private ExecutorService spielerPool;
        private boolean letzterClientStatus = false;// min 1, max Anzahl definierte Farben in Enum YoolooKartenSpiel.KartenFarbe)

	/**
	 * Serverseitig durch ClientHandler angebotenen SpielModi. Bedeutung der
	 * einzelnen Codes siehe Inlinekommentare.
	 * 
	 * Derzeit nur Modus Play Single Game genutzt
	 */
	public enum GameMode {
		GAMEMODE_NULL, // Spielmodus noch nicht definiert
		GAMEMODE_SINGLE_GAME, // Spielmodus: einfaches Spiel
		GAMEMODE_PLAY_ROUND_GAME, // noch nicht genutzt: Spielmodus: Eine Runde von Spielen
		GAMEMODE_PLAY_LIGA, // noch nicht genutzt: Spielmodus: Jeder gegen jeden
		GAMEMODE_PLAY_POKAL, // noch nicht genutzt: Spielmodus: KO System
		GAMEMODE_PLAY_POKAL_LL // noch nicht genutzt: Spielmodus: KO System mit Lucky Looser
	};

	public YoolooServer(int port, int spielerProRunde, GameMode gameMode) {
		this.port = port;
		this.spielerProRunde = spielerProRunde;
		this.serverGameMode = gameMode;
	}

	public void startServer() {
		try {
			// Init
			serverSocket = new ServerSocket(port);
			spielerPool = Executors.newCachedThreadPool();
			clientHandlerList = new ArrayList<YoolooClientHandler>();
			System.out.println("Server gestartet - warte auf Spieler");

			while (serverAktiv) {
				Socket client = null;

                                try {
					client = serverSocket.accept();				    
					PrintWriter out =
				            new PrintWriter(client.getOutputStream(), true);
				        BufferedReader in = new BufferedReader(
				            new InputStreamReader(client.getInputStream()));
				    String inputLine, outputLine;
				    ZuschauerProtokoll kkp = new ZuschauerProtokoll();
				    outputLine = kkp.processInput(null);
				    out.println(outputLine);
			        while ((inputLine = in.readLine()) != null) {
			            outputLine = kkp.processInput(inputLine);
			            out.println(outputLine);
			            if (outputLine.equals("Zuschauer")) {
			            	this.zuschauer = this.zuschauer + 1;
			            	this.letzterClientStatus = true;
			            	System.out.println("[YoolooServer] Anzahl verbundene Zuschauer: " + this.zuschauer);
			            	break;
			            }
			            if (outputLine.equals("Spieler")) {
			            	this.spieler = this.spieler + 1;
			            	this.letzterClientStatus = false;
			            	System.out.println("[YoolooServer] Anzahl verbundene Spieler: " + this.spieler);
			            	break;
			            }
			      
			                
			        }
			        if (this.letzterClientStatus == true) {
			        	YoolooClientHandler clientHandler = new YoolooClientHandler(this, client, true);
			        	clientHandlerList.add(clientHandler);
			        }
			        else  {
			        	YoolooClientHandler clientHandler = new YoolooClientHandler(this, client, false);
			        	clientHandlerList.add(clientHandler);
			        }
					
				} catch (IOException e) {
					System.out.println("Client Verbindung gescheitert");
					e.printStackTrace();
				}
				// Neue Spieler registrieren
				
			}
		} catch (IOException e1) {
			System.out.println("ServerSocket nicht gebunden");
			serverAktiv = false;
			e1.printStackTrace();
		}

	}

	// TODO Dummy zur Serverterminierung noch nicht funktional
	public void shutDownServer(int code) {
		if (code == 543210) {
			this.serverAktiv = false;
			System.out.println("Server wird beendet");
			spielerPool.shutdown();
		} else {
			System.out.println("Servercode falsch");
		}
	}
}
