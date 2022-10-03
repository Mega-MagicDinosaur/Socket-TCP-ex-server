package com.fi.meucci;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerStr {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = "";
    String stringaModificata = "";
    BufferedReader inClient;
    DataOutputStream outClient;

    public ServerStr () {
        try { server = new ServerSocket(5500); }
        catch (Exception  exc) { System.out.println(exc.getMessage()); }
    }

    public Socket attendi() {
        try {
            System.out.println("listening on port 5500");
            client = server.accept();
            // server.close();
            inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception exc) { System.out.println(exc.getMessage()); }

        return client;
    }

    public void comunica() {
        try {
            stringaRicevuta = inClient.readLine();
            System.out.println("line recieved");

            stringaModificata = stringaRicevuta.toUpperCase();
            System.out.println("sending modified string");
            outClient.writeBytes(stringaModificata + '\n');
            System.out.println("string has been sent");
            client.close();
        } catch(Exception exc) { System.out.println(exc.getMessage()); }
    }
}
