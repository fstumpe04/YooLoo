/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allgemein;

import client.YoolooSpectatorClient;

/**
 *
 * @author santosr
 */
public class StarterSpectatorClient {
    public static void main(String[] args) {
        // Starte Client
		String hostname = "localhost";
//		String hostname = "10.101.251.247";
		int port = 44137;
		YoolooSpectatorClient spectatorClient = new YoolooSpectatorClient(hostname, port);
		spectatorClient.startSpectatorClient();
        
    }
    
}
