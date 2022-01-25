// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package server;

import common.YoolooKarte;
import common.YoolooKartenspiel;
import common.YoolooStich;
import server.YoolooServer.GameMode;

public class YoolooSession {

	private int anzahlSpielerInRunde;
	private GameMode gamemode = GameMode.GAMEMODE_NULL;
	private YoolooKarte[][] spielplan;
	private YoolooKartenspiel aktuellesSpiel;
	private YoolooStich[] ausgewerteteStiche;

	public YoolooSession(int anzahlSpielerInRunde) {
		super();
		this.anzahlSpielerInRunde = anzahlSpielerInRunde;
		gamemode = GameMode.GAMEMODE_NULL;
		spielplan = new YoolooKarte[YoolooKartenspiel.maxKartenWert][anzahlSpielerInRunde];
		ausgewerteteStiche = new YoolooStich[YoolooKartenspiel.maxKartenWert];
		for (int i = 0; i < ausgewerteteStiche.length; i++) {
			ausgewerteteStiche[i] = null;
		}
		aktuellesSpiel = new YoolooKartenspiel();
	}

	public YoolooSession(int anzahlSpielerInRunde, GameMode gamemode) {
		this(anzahlSpielerInRunde);
		this.gamemode = gamemode;
	}

	public synchronized void spieleKarteAus(int stichNummer, int spielerID, YoolooKarte karte) {
		spielplan[spielerID][stichNummer] = karte;
	}

	public synchronized YoolooStich stichFuerRundeAuswerten(int stichNummer) {
		if (ausgewerteteStiche[stichNummer] == null) {
			YoolooStich neuerStich = null;
			YoolooKarte[] karten = spielplan[stichNummer];
			for (int spielernummer = 0; spielernummer < spielplan[stichNummer].length; spielernummer++) {
				if (spielplan[stichNummer][spielernummer] == null) {
					karten = null;
				}
			}
			if (karten != null) {
				neuerStich = new YoolooStich(karten);
				neuerStich.setStichNummer(stichNummer);
				neuerStich.setSpielerNummer(aktuellesSpiel.berechneGewinnerIndex(karten));
				ausgewerteteStiche[stichNummer] = neuerStich;
				System.out.println("Stich ausgewertet:" + neuerStich.toString());
			}
		}
		return ausgewerteteStiche[stichNummer];

	}

	public YoolooKartenspiel getAktuellesSpiel() {
		return aktuellesSpiel;
	}

	public void setAktuellesSpiel(YoolooKartenspiel aktuellesSpiel) {
		this.aktuellesSpiel = aktuellesSpiel;
	}

	public int getAnzahlSpielerInRunde() {
		return anzahlSpielerInRunde;
	}

	public void setAnzahlSpielerInRunde(int anzahlSpielerInRunde) {
		this.anzahlSpielerInRunde = anzahlSpielerInRunde;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public YoolooKarte[][] getSpielplan() {
		return spielplan;
	}

	public void setSpielplan(YoolooKarte[][] spielplan) {
		this.spielplan = spielplan;
	}

	public String getErgebnis() {
		// TODO mit Funktion fuellen
		String ergebnis = "Ergebnis:\n blabla";
		return ergebnis;
	}

}
