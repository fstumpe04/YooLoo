// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

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

	// Sortierung wird zufuellig ermittelt
	public void sortierungFestlegen() {
            //for(YoolooKarte card : aktuelleSortierung)
            //    System.out.println(card);
            Scanner sc = new Scanner(System.in);
            // Karten mischen?
            int sortAlgo;
            do{
                System.out.println("++++++++++++++++++++");
                System.out.println("+   1.  Random     +");
                System.out.println("+   2.  select     +");
                System.out.println("+   3.  last5high  +");
                System.out.println("++++++++++++++++++++");
                sortAlgo = sc.nextInt();//().charAt(0);
            } while(sortAlgo < 0 && sortAlgo >= 3);
            
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
                default:
                    System.out.println("Diese Option steht nicht zur Verfügung...");
                    sortierungFestlegen();
                    break;
            }
            for(YoolooKarte aus : getAktuelleSortierung()){
                System.out.println(aus);
            }
	}
        
        public YoolooKarte[] sorting(int choise){
            YoolooKarte[] neueSortierung = new YoolooKarte[this.aktuelleSortierung.length];
            Scanner sc = new Scanner(System.in);
            
            switch(choise){
                case 1:
                    for (int i = 0; i < neueSortierung.length; i++) {
                        int neuerIndex = (int) (Math.random() * neueSortierung.length);
                        while (neueSortierung[neuerIndex] != null) {
                            neuerIndex = (int) (Math.random() * neueSortierung.length);
                        }
                        neueSortierung[neuerIndex] = aktuelleSortierung[i];
                    }
                    break;
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
                            neueSortierung[neuePos] = aktuelleSortierung[i];//aktuelleSortierung[i];
                        else{
                            System.err.println("Position " + neuePos + " ist bereits belegt");
                            i--;
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < neueSortierung.length; i++) {       // neuerIndex = Pos in neueSortierung // i = Kartenwert
                        System.out.println("Karte zum einfügen: " + aktuelleSortierung[i]);
                        int neuerIndex = (int) (Math.random() * 5);
                        
                        // Random sort of first 5 cards (1-5)
                        if(i < 5){
                            while(neueSortierung[neuerIndex] != null){
                                neuerIndex = (int) (Math.random() * 5);
                            }
                        } 
                        // Random sort of last 5 cards (6-10)
                        else 
                        {
                            neuerIndex += 5;
                            while(neueSortierung[neuerIndex] != null){
                                neuerIndex = ((int) (Math.random() * 5));
                                neuerIndex += 5;
                            }
                        }
                        neueSortierung[neuerIndex] = aktuelleSortierung[i];
                    }
                    break;
                case 4:
                    for (int i = 0; i < neueSortierung.length; i++){
                        if(i < 3){  //
                        }
                        else if(i < 6){
                        }
                        else{
                        }
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
