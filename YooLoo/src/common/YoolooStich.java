// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package common;

import java.io.Serializable;
import java.util.Arrays;

public class YoolooStich implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1967495934818226176L;
	private int stichNummer; // Punktewert des Stichs
	private int spielerNummer; // SpielerNummer des Stichs
	private YoolooKarte[] karten;

	public YoolooStich() {
		super();
	}

	public YoolooStich(YoolooKarte[] stich) {
		super();
		this.stichNummer = -1;
		this.spielerNummer = -1;
		this.karten = stich;
	}

	public int getStichNummer() {
		return stichNummer;
	}

	public void setStichNummer(int stichNummer) {
		this.stichNummer = stichNummer;
	}

	public int getSpielerNummer() {
		return spielerNummer;
	}

	public void setSpielerNummer(int spielerNummer) {
		this.spielerNummer = spielerNummer;
	}

	public YoolooKarte[] getStich() {
		return karten;
	}

	public void setStich(YoolooKarte[] stich) {
		this.karten = stich;
	}

	@Override
	public String toString() {
		return "YoolooStich [stichNummer=" + stichNummer + ", spielerNummer=" + spielerNummer + ", karten="
				+ Arrays.toString(karten) + "]";
	}

}
