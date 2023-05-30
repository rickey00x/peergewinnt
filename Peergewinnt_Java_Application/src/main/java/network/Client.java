package network;

import Gui.GameWindow.ButtonObserver;
import Gui.GameWindow.GameWindow;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable, ButtonObserver {

    GameWindow gameWindow;
    boolean keepAlive;
    int lastUserAction = -1;
    private String hostName;
    private int portNumber = 6602;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(GameWindow gameWindow, String hostName) {
        this.hostName = hostName;
        setUpConnection();
        this.gameWindow = gameWindow;
        keepAlive = true;//set this to false once the connection is close
    }

    private void setUpConnection() {
        try {
            clientSocket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {
        while (keepAlive) {
            try {
                DTOToClient msg = (DTOToClient) in.readObject();
                gameWindow.updateMatrix(msg.matrix());
                if (msg.gameOver()) {
                    keepAlive=false;
                    JOptionPane.showConfirmDialog(gameWindow,"Game over");
                }
                if (msg.sendAnswer()) {
                    gameWindow.setActive(true);
                    while (true) {
                        if (lastUserAction == -1) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            sendAnswer(lastUserAction);
                            gameWindow.setActive(false);
                            lastUserAction = -1;
                            break;
                        }
                    }

                }
            } catch (Exception e) {
                String thing = e.getMessage();
            }
        }
    }


    //do some sending
    private void sendAnswer(int msg) throws IOException {
        out.writeObject(new DTOToServer(msg));
    }

    @Override
    public void notify(int selection) {
        lastUserAction = selection;
    }
}
