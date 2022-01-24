// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change


package messages;

import java.io.Serializable;

public class ClientMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public ClientMessage(ClientMessageType clientMessageType, String messagetext) {
		this.message = messagetext;
		this.type = clientMessageType;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ClientMessageType getType() {
		return type;
	}

	public void setType(ClientMessageType type) {
		this.type = type;
	}

	private ClientMessageType type;

	@Override
	public String toString() {
		return "ClientMessage [type=" + type + ", message=" + message + "]";
	}

	public enum ClientMessageType {
		ClientMessage_OK, //
		ClientMessage_NOT_OK
	};

}
