// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change 


package messages;


import java.io.Serializable;

import client.YoolooClient.ClientState;

/**
 * Serverseitig durch ClientHandler generierte Nachrichten zur Ubermittlung an
 * Client
 *
 */
public class ServerMessage implements Serializable {

	private static final long serialVersionUID = 3838258656446288816L;

	private ServerMessageType serverMessageType;
	private ClientState nextClientState;
	private ServerMessageResult serverMessageResult; // noch nicht genutzt
	private int paramInt; // fuer (sequenzielle) Durchfuehrung von wiederholten Operationen

	/**
	 * Serverseitig durch ClientHandler generierte Nachrichtencodes zur Steuerung
	 * des Clients Bedeutung der einzelnen Codes siehe Inlinekommentare
	 *
	 */
	public enum ServerMessageType {
		SERVERMESSAGE_ACKNOWLEDGE, // t.b.d.
		SERVERMESSAGE_SENDLOGIN, // Übermittle registierten Spieler zurueck an Client
		SERVERMESSAGE_SORT_CARD_SET, // Spieler legen Ihre Sortierung fest
		SERVERMESSAGE_SEND_CARD, // Karten werden an Spieler ausgegeben
		SERVERMESSAGE_RESULT_SET, // Stich wird an Spieler zurueckgegeben
		SERVERMESSAGE_CHANGE_STATE // Manuelle Steuerung des Clienstatus

	};

	/**
	 * Serverseitig durch ClientHandler generierte Rueckmeldungen zur Information des
	 * Clients Bedeutung der einzelnen Codes siehe Inlinekommentare Derzeit nicht
	 * genutzt
	 */
	public enum ServerMessageResult {
		SERVER_MESSAGE_RESULT_OK, // noch nicht genutzt - positive Bestuetigung einer Transaktion
		SERVER_MESSAGE_RESULT_NOT_OK // noch nicht genutzt - negative Quittung einer Transaktion

	};

	public ServerMessage() {
		super();
		this.serverMessageType = ServerMessageType.SERVERMESSAGE_ACKNOWLEDGE;
		this.nextClientState = ClientState.CLIENTSTATE_LOGIN;
		this.serverMessageResult = ServerMessageResult.SERVER_MESSAGE_RESULT_OK;
	}

	public ServerMessage(ServerMessageType type, ClientState nextState, ServerMessageResult result) {
		super();
		this.serverMessageType = type;
		this.nextClientState = nextState;
		this.serverMessageResult = result;
		this.paramInt = -1;
	}

	public ServerMessage(ServerMessageType type, ClientState nextState, ServerMessageResult result, int paramInt) {
		super();
		this.serverMessageType = type;
		this.nextClientState = nextState;
		this.serverMessageResult = result;
		this.paramInt = paramInt;
	}

	public ServerMessageType getServerMessageType() {
		return serverMessageType;
	}

	public void setServerMessageType(ServerMessageType serverMessageType) {
		this.serverMessageType = serverMessageType;
	}

	public ClientState getNextClientState() {
		return nextClientState;
	}

	public void setNextClientState(ClientState nextClientState) {
		this.nextClientState = nextClientState;
	}

	public ServerMessageResult getServerMessageResult() {
		return serverMessageResult;
	}

	public void setServerMessageResult(ServerMessageResult serverMessageResult) {
		this.serverMessageResult = serverMessageResult;
	}

	public int getParamInt() {
		return paramInt;
	}

	public void setParamInt(int paramInt) {
		this.paramInt = paramInt;
	}

	@Override
	public String toString() {
		return "ServerMessage [serverMessageType=" + serverMessageType + ", nextClientState=" + nextClientState
				+ ", serverMessageResult=" + serverMessageResult + ", paramInt=" + paramInt + "]";
	}

}
