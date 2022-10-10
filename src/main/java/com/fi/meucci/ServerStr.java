package com.fi.meucci;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerStr {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = "";
    String stringaModificata = "";
    BufferedReader inClient;
    DataOutputStream outClient;

    ArrayList<Socket> sockets = new ArrayList<>();
    
    public void avvia() {
        try { 
            server = new ServerSocket(5500);
            while(true) {
                System.out.println("listening to new clients on port 5500");
                client = server.accept();
                sockets.add(client);
                Thread thread = new Thread(new Runnable() {
                    @Override public void run() { comunica(); }
                });
                thread.start();
            }
        }
        catch (Exception  exc) { System.out.println(exc.getMessage()); }
    }

    public void comunica() {
            try {
                System.out.println("connection with client started, listening to incoming messages");
                inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                outClient = new DataOutputStream(client.getOutputStream());
                
                while (true) {
                    stringaRicevuta = inClient.readLine();
                    if (stringaRicevuta == null || stringaRicevuta == "" || stringaRicevuta.equals("EXIT")) { break; }
                    if (stringaRicevuta.equals("CLOSE")) { 
                        for (Socket socket : sockets) { socket.close(); }
                        server.close();
                        System.exit(0);
                     }
                    System.out.println("line recieved");
                    stringaModificata = stringaRicevuta.toUpperCase();
                    System.out.println("sending modified string");
                    outClient.writeBytes(stringaModificata + '\n');
                    System.out.println("string has been sent");
                }
                outClient.close();
                inClient.close();
                client.close();
            } catch(Exception exc) { System.out.println(exc.getMessage()); }
    }
}
