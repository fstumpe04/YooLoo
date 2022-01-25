// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package allgemein;

import common.YoolooKartenspiel;

public class Starter {
	public static void main(String[] args) {

//		Initialisieren des Spiels

		YoolooKartenspiel yooloo = new YoolooKartenspiel();
		yooloo.listeSpielstand();

//		Registrieren der Spieler
		yooloo.spielerRegistrieren("Juergen");
		yooloo.spielerRegistrieren("Reiner");
		yooloo.spielerRegistrieren("Benedikt");
		yooloo.spielerRegistrieren("Olaf");

//		Sortieren der Karten
		yooloo.spielerSortierungFestlegen();

//		Ausspielend der Runden
		yooloo.spieleRunden();

//		Spielstand anzeigen
		yooloo.listeSpielstand();

	}
}
