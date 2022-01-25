// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package common;

import java.io.Serializable;

import server.YoolooServer.GameMode;

public class LoginMessage implements Serializable {

	private static final long serialVersionUID = -4012991046304922214L;

	private String spielerName;
	private GameMode gameMode;

	public LoginMessage(String name, GameMode play_mode) {
		super();
		this.spielerName = name;
		this.gameMode = play_mode;
	}

	public LoginMessage() {
		super();
		this.gameMode = GameMode.GAMEMODE_SINGLE_GAME;
	}

	public LoginMessage(String spielerName) {
		super();
		this.spielerName = spielerName;
		this.gameMode = GameMode.GAMEMODE_SINGLE_GAME;
	}

	public String getSpielerName() {
		return spielerName;
	}

	public void setSpielerName(String spielerName) {
		this.spielerName = spielerName;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	@Override
	public String toString() {
		return "LoginMessage [spielerName=" + spielerName + ", gameMode=" + gameMode + "]";
	}

}
