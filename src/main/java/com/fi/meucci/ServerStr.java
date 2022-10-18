package com.fi.meucci;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerStr {
    long startTime = 0;

    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = "";
    String stringaModificata = "";
    BufferedReader inClient;
    DataOutputStream outClient;

    ArrayList<Socket> sockets = new ArrayList<>();
    
    public void avvia() {
        startTime = System.currentTimeMillis();
        try { 
            server = new ServerSocket(5500);
            System.out.println("started listening on port 5500: " + getTimer());
            while(true) {
                System.out.println("server is ready to accept new clients: " + getTimer());
                client = server.accept();
                sockets.add(client);

                Thread thread = new Thread(new Runnable() {
                    @Override public void run() { comunica(); }
                });
                thread.start();

                System.out.println("started new server thread for communication: " + getTimer());
            }
        }
        catch (IOException e) { System.out.println(e.getMessage()); }
    }

    public void termina() {
        try {
            for (Socket socket : sockets) { 
                DataOutputStream socketOutputStream = new DataOutputStream(socket.getOutputStream());
                socketOutputStream.writeBytes("EXIT" + '\n');
                socket.close(); 
            }
            server.close();
            // System.exit(0);
        } catch(IOException e) { System.out.println(e.getMessage()); }
    }

    public void comunica() {
        try {
            System.out.println("\n == starting connection with client == : " + getTimer());
            inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outClient = new DataOutputStream(client.getOutputStream());
            System.out.println("connection with client started, listening to incoming messages: " + getTimer());

            while (true) {
                stringaRicevuta = inClient.readLine();
                System.out.println("line recieved: " + stringaRicevuta + ": " + getTimer());
                
                if (stringaRicevuta == null || stringaRicevuta.equals("") || stringaRicevuta.equals("EXIT")) { break; }
                else if (stringaRicevuta.equals("CLOSE")) { termina(); }
                else {
                    System.out.println("parsing recieved message: " + getTimer());
                    stringaModificata = stringaRicevuta + '!';
                    System.out.println("sending parsed message to client: " + getTimer());
                    outClient.writeBytes(stringaModificata + '\n');
                    System.out.println("parsed message has been sent to client: " + getTimer());
                }
            }
            System.out.println("closing connection with client: " + getTimer());
                
            outClient.close();
            inClient.close();
            client.close();

            System.out.println("connection with client closed: " + getTimer());
        } catch(IOException e) { System.out.println(e.getMessage()); }
    }

    public long getTimer() { return System.currentTimeMillis() - startTime; }
}
