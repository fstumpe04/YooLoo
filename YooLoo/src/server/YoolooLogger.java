package server;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author Akin
 */

public class YoolooLogger {
    public YoolooLogger(){
        //setup Logger
    setupLogger();
    }
    
    public static final Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
    public static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        
        //Console Handler erstellen
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);
        
        //Error Erzeugen und loggen
        try {
            FileHandler fh = new FileHandler("YooLooLog.log", true);
            fh.setLevel(Level.FINE);
            logger.addHandler(fh);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "Logger funktioniert nicht", e);
        }
    }
    
    
    //Verschiedene Level zum Loggen der Nachrichten
    public enum MessageLevel{
          /*OFF,
          SEVERE = 0
          WARNING = 1
          INFO = 2
          CONFIG = 3
          FINE = 4
          FINER = 5
          FINEST = 6
          ALL
        */
        }
    
    //
    public void writeMessage(String infoMessage,int levelType) {
        
        //Arten des Logs
        switch(levelType){
            case 0: logger.severe(infoMessage);
            break;
            case 1: logger.warning(infoMessage);
            break;
            case 2: logger.info(infoMessage);
            break;
            case 3: logger.config(infoMessage);
            break;
            case 4: logger.fine(infoMessage);
            break;
            case 5: logger.finer(infoMessage);
            break;
            case 6: logger.finest(infoMessage);
            break;
            default: System.out.println("Logger Typ exisitert nicht " + levelType);
            break;
        }
    }
}

// public static YoolooLogger logger;
// logger.writeMessage("Client Verbindung gescheitert", 0);
// logger.writeMessage("testlogger", i );
    
    /*
    public static void main(String[] args) throws java.io.IOException {
        setupLogger();
        
        logger.info("erster log");
        logger.fine("zweiter log");
        
        Test.test();

        try {
            throw new java.io.IOException("Fake Error");
        }
        catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "Ein Fake Error erscheint", e);
            throw e;
        }
        
    }
    
    */
