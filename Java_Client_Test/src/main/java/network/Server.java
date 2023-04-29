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

        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + this.portNumber);
        }

        System.out.println("Server started");
        try {
            System.out.println("Waiting for client");
            this.clientSocket = serverSocket.accept();

        } catch (IOException e) {
            System.out.println("Accept failed: " );
        }

        System.out.println("Client connected");

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to: " + this.hostName);
        }

        while (!serverSocket.isClosed()) {
            out.println("Hello from server");
            try {

                line = in.readLine();
                if(line != null){
                    System.out.println("In on Server: " + line);
                    out.println(line + " from server");

                    if(line.equals("tschüss")){
                        out.close();
                        in.close();
                        clientSocket.close();
                        serverSocket.close();
                    }
                }


            } catch (IOException e) {
                System.out.println("Read failed");
            }


        }

    }
}