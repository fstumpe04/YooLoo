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
            Scanner sc = new Scanner(System.in);
            // Karten mischen?
            char sort;
            do{
                System.out.println("+++ Karten Zufaellig mischen? (y/n) +++");
                sort = sc.next().charAt(0);
            } while(!(sort == 'y' || sort == 'n'));
            
            if(sort == 'y'){
		/*YoolooKarte[] neueSortierung = new YoolooKarte[this.aktuelleSortierung.length];
		for (int i = 0; i < neueSortierung.length; i++) {
			int neuerIndex = (int) (Math.random() * neueSortierung.length);
			while (neueSortierung[neuerIndex] != null) {
				neuerIndex = (int) (Math.random() * neueSortierung.length);
			}
			neueSortierung[neuerIndex] = aktuelleSortierung[i];
			// System.out.println(i+ ". neuerIndex: "+neuerIndex);
		}
		aktuelleSortierung = neueSortierung;*/
                aktuelleSortierung = sorting(1);
            }
            else if (sort == 'n'){
                /*      Spieler Legt Reihenfolge fest       */
               /* YoolooKarte[] userSortierung = new YoolooKarte[this.aktuelleSortierung.length];
                for(int i =  0; i < userSortierung.length; i++){
                    System.out.println("\n\nPosition für Karte\n ->\t" + aktuelleSortierung[i]);
                    int neuePos = -1;
                    do{
                        // Output of current sorting
                        int numCards = 0;
                        System.out.println("------- Sortierung -------\nPosition\tKarte");
                        for(YoolooKarte currentCard : userSortierung){
                            System.out.println(numCards + "\t\t" + currentCard);
                            numCards++;
                        }
                        neuePos = sc.nextInt();
                        
                    }while (neuePos < 0 || neuePos > 10);
                    if(userSortierung[neuePos] == null)
                        userSortierung[neuePos] = aktuelleSortierung[i];//aktuelleSortierung[i];
                    else{
                        System.err.println("Position " + neuePos + " ist bereits belegt");
                        i--;
                    }
                }
                aktuelleSortierung = userSortierung;*/
               aktuelleSortierung = sorting(3);
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
                    for (int i = 0; i < neueSortierung.length; i++) {
                        int neuerIndex = (int) (Math.random() * neueSortierung.length);
                        if(i < 7){
                            while (neueSortierung[neuerIndex] != null && neuerIndex >= 7) {
                                neuerIndex = (int) (Math.random() * neueSortierung.length);
                            }
                        } else {
                            while (neueSortierung[neuerIndex] != null && neuerIndex <= 7) {
                                neuerIndex = (int) (Math.random() * neueSortierung.length);
                            }
                        }
                        neueSortierung[neuerIndex] = aktuelleSortierung[i];
                    }
                    break;
                    
            
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
