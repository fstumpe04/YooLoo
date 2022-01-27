// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change
//  V0.2    |220127| pas |    -   | add sortings for 'sortierungFestlegen()'

package common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

import common.YoolooKartenspiel.Kartenfarbe;

public class YoolooSpieler implements Serializable {

	private static final long serialVersionUID = 376078630788146549L;
	private String name;
	private Kartenfarbe spielfarbe;
	private int clientHandlerId = -1;
	private int punkte;
	private YoolooKarte[] aktuelleSortierung;

	public YoolooSpieler(String name, int maxKartenWert) {
		this.name = name;
		this.punkte = 0;
		this.spielfarbe = null;
		this.aktuelleSortierung = new YoolooKarte[maxKartenWert];
	}

       /**
        * Client waehlt option zum Sortieren der Karten.
        * @author Paskuda Lukas (überarbeitet)
        * @see common.YoolooKarte[] sorting(int choise)
        */
	public void sortierungFestlegen() {
            Scanner sc = new Scanner(System.in);
            int sortAlgo = 0;
                System.out.println("\n++ Strategie wählen ++");
                System.out.println("++++++++++++++++++++++");
                System.out.println("+   1.  Random       +   --- Mische das gesamte Set");
                System.out.println("+   2.  select       +   --- Wähle jede Position selbst");
                System.out.println("+   3.  1-5|6-10     +   --- Gemischt: 1-5 und 6-10");
                System.out.println("+   4.  1-3|4-6|7-10 +   --- Gemischt: 1-3, 4-6 und 7-10");
                System.out.println("++++++++++++++++++++++");
                
                // try-catch: Sicherstellen, dass Zahl (int) eingegeben wird
                try{
                    sortAlgo = sc.nextInt();
                } catch(java.util.InputMismatchException e) {
                    System.err.println("Bitte eine Zahl angeben");
                }
            
            switch (sortAlgo) {
                case 1:
                    aktuelleSortierung = sorting(1);
                    break;
                case 2:
                    aktuelleSortierung = sorting(2);
                    break;
                case 3:
                    aktuelleSortierung = sorting(3);
                    break;
                case 4:
                    aktuelleSortierung = sorting(4);
                    break;
                default:
                    System.err.println("Diese Option steht nicht zur Verfügung...");
                    sortierungFestlegen();
                    break;
            }
            for(YoolooKarte karte : getAktuelleSortierung()){
                System.out.println(karte);
            }
	}
        
	
       /**
        * Ueber die Eingabe in <b><i>"sortierungFestlegen()"</i></b> wird entsprechend der Eingabe ein Algorithmus zum Sortieren der Karten ausgefuehrt.
        *
        * <br><br>Es stehend diese Optionen zur Verfuegung:
        *               <br><span style="margin-left:30px"><b>choise = 1:</b> Mische gesamtes Kartenset</span>
        *               <br><span style="margin-left:30px"><b>choise = 2:</b> Client wählt position fuer jede Karte</span>
        *               <br><span style="margin-left:30px"><b>choise = 3:</b> Mische die Positionen 1-5 und 6-10</span>
        *               <br><span style="margin-left:30px"><b>choise = 4:</b> Mische die Positionen 1-3, 4-6 und 7-10</span>
        *
        * @author Paskuda Lukas
        * @param choise switch Parameter fuer Auswahl des Sortieralgorithmus
        * @return YoolooKarten[] neueSortierung
        */
        public YoolooKarte[] sorting(int choise){
            YoolooKarte[] neueSortierung = new YoolooKarte[this.aktuelleSortierung.length];
            Scanner sc = new Scanner(System.in);
            
            switch(choise){
                
                // case 1: Mische das gesamte Set
                case 1:
                    for (int i = 0; i < neueSortierung.length; i++) {
                        int neuerIndex = (int) (Math.random() * neueSortierung.length);
                        while (neueSortierung[neuerIndex] != null) {
                            neuerIndex = (int) (Math.random() * neueSortierung.length);
                        }
                        neueSortierung[neuerIndex] = aktuelleSortierung[i];
                    }
                    break;
                    
                // case 2: Client legt Sortierung fest
                case 2:   
                    for(int i =  0; i < neueSortierung.length; i++){
                        System.out.println("\n\nPosition für Karte\n ->\t" + aktuelleSortierung[i]);
                        int neuePos = -1;
                        do{
                            // Output of current sorting
                            int numCards = 0;
                            System.out.println("------- Sortierung -------\nPosition\tKarte");
                            for(YoolooKarte currentCard : neueSortierung){
                                System.out.println(numCards + "\t\t" + currentCard);
                                numCards++;
                            }
                            neuePos = sc.nextInt();

                        }while (neuePos < 0 || neuePos > 10);
                        if(neueSortierung[neuePos] == null)
                            neueSortierung[neuePos] = aktuelleSortierung[i];
                        else{
                            System.err.println("Position " + neuePos + " ist bereits belegt");
                            i--;
                        }
                    }
                    break;
                    
                // case 3: Mische die Karten 1-5 und die Karten 6-10 
                case 3:
                    for (int i = 0; i < neueSortierung.length; i++) {
                        System.out.println("Karte zum einfügen: " + aktuelleSortierung[i]);
                        int neuerIndex = (int) (Math.random() * 5);
                        do{
                            neuerIndex = (int) (Math.random() * 5);
                            if(i >= 5)
                                neuerIndex += 5;
                        }while (neueSortierung[neuerIndex] != null);
                        neueSortierung[neuerIndex] = aktuelleSortierung[i];
                    }
                    break;
                    
                // case 4: Mische die Karten 1-3, 4-6 und 7-10
                case 4:
                    for (int i = 0; i < neueSortierung.length; i++){
                        int neuerIndex ;
                        if(i < 6){
                            do{
                                neuerIndex = (int) (Math.random() * 3);
                                if(i >= 3)
                                    neuerIndex += 3;
                            }while (neueSortierung[neuerIndex] != null);
                        }
                        else{
                            do{
                                neuerIndex = ((int) (Math.random() * 4));
                                neuerIndex += 6;
                            }while (neueSortierung[neuerIndex] != null);
                        }
                        neueSortierung[neuerIndex] = aktuelleSortierung[i];
                       //neueSortierung[i] = aktuelleSortierung[9]; //cheating
                    }
                    
            }
            return neueSortierung;
        }

	public int erhaeltPunkte(int neuePunkte) {
		System.out.print(name + " hat " + punkte + " P - erhaelt " + neuePunkte + " P - neue Summe: ");
		this.punkte = this.punkte + neuePunkte;
		System.out.println(this.punkte);
		return this.punkte;
	}

	@Override
	public String toString() {
		return "YoolooSpieler [name=" + name + ", spielfarbe=" + spielfarbe + ", puntke=" + punkte
				+ ", altuelleSortierung=" + Arrays.toString(aktuelleSortierung) + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Kartenfarbe getSpielfarbe() {
		return spielfarbe;
	}

	public void setSpielfarbe(Kartenfarbe spielfarbe) {
		this.spielfarbe = spielfarbe;
	}

	public int getClientHandlerId() {
		return clientHandlerId;
	}

	public void setClientHandlerId(int clientHandlerId) {
		this.clientHandlerId = clientHandlerId;
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int puntke) {
		this.punkte = puntke;
	}

	public YoolooKarte[] getAktuelleSortierung() {
		return aktuelleSortierung;
	}

	public void setAktuelleSortierung(YoolooKarte[] aktuelleSortierung) {
		this.aktuelleSortierung = aktuelleSortierung;
	}

	public void stichAuswerten(YoolooStich stich) {
		System.out.println(stich.toString());

	}

}
