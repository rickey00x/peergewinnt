package network;

import java.io.*;
import java.net.*;

public class Client implements Runnable{
    private String hostName = "localhost";
    private int portNumber = 6602;
    private Socket clientSocket = null;

    public Client(Socket client){
        this.clientSocket = client;
    }

    public Client(int portNumber, String hostName){
        this.portNumber = portNumber;
        this.hostName = hostName;
    }

    public void run(){
PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;

        try {
            this.clientSocket = new Socket(this.hostName, this.portNumber);
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host: " + this.hostName);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to: " + this.hostName);
        }

        System.out.println("Client started");

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to: " + this.hostName);
        }

        String fromServer;
        String fromUser;

        while (!clientSocket.isClosed()) {
            try {
                fromServer = in.readLine();
                if(fromServer != null) {
                    System.out.println("In on Client: " + fromServer);
                    out.println("Hello from client");
                }
            } catch (IOException e) {
                System.out.println("Read failed");
            }

            try {
                out.println("tschüss");
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close I/O");

            }
        }
    }
}
