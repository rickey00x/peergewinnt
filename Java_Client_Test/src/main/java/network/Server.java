package network;

import java.io.*;
import java.net.*;

public class Server implements Runnable {
    private String hostName = "localhost";
    private int portNumber;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server(int port) {
        this.portNumber = port;
    }

    public void run() {
        String line = "";
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;

        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch (IOException e) {
            System.out.println("Could not listen on port");
        }

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Accept failed");
        }

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Read failed");
        }

        while (true) {

            try {
                line = in.readLine();
                out.println(line + " from server");


            } catch (IOException e) {
                System.out.println("Read failed");
            }

            System.out.println("line: " + line);
        }

    }

    protected void finalize() {
//Objects created in run method are finalized when
//program terminates and thread exits
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
        }
    }
}