package server;


import java.net.*;
import java.io.*;
 
public class ZuschauerProtokoll {
    private static final int WAITING = 0;
    private static final int SENTQUESTION = 1;
 
    private int state = WAITING;
 
    public String processInput(String theInput) {
        String theOutput = null;
 
        if (state == WAITING) {
            theOutput = "Zuschauer oder Spieler?";
            state = SENTQUESTION;
        } else if (state == SENTQUESTION) {
            if (theInput.equalsIgnoreCase("Zuschauer")) {
                theOutput = "Zuschauer";
                state = WAITING;
            } else if (theInput.equalsIgnoreCase("Spieler")){
                theOutput = "Spieler";
                state = WAITING;
            }
            else {
                theOutput = "Zuschauer oder Spieler sagen!!" +
                "Zuschauer oder Spieler?";
            }
        }
        return theOutput;
    }
}