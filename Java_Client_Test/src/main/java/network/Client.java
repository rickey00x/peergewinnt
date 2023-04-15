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
        String line;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;

        for(int i = 0; i < 10; i++){
            try{
                if(clientSocket == null)
                    this.clientSocket = new Socket(this.hostName, this.portNumber);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                stdIn = new BufferedReader(new InputStreamReader(System.in));
                out.println("Test string from client "+ i);
                line = in.readLine();
                System.out.println("Client line: " + line);
            }catch (UnknownHostException e) {
                System.out.println("Don't know about host");
            }catch (IOException e) {
                System.out.println("in or out failed");
            }
        }

    }
}
