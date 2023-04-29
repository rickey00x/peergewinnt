package network;

import Gui.GameWindow.ButtonObserver;
import Gui.GameWindow.GameWindow;
import model.Game;
import model.InvalidMoveException;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 implements Runnable, ButtonObserver {
    private String hostName = "localhost";
    private int portNumber = 6602;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Game game;
    private GameWindow gameWindow;
    private String server;
    private int lastMove=-1;

    public Server2(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        JOptionPane.showConfirmDialog(gameWindow,"Waiting for clients...");
        setupConnection();
        server = "You";
        game = new Game(server, "Name");
    }

    @Override
    public void run() {
        while (!game.gameOver){
            gameWindow.updateMatrix(game.getPlayingField());
            if(game.getCurrentPlayer().equalsIgnoreCase(server)){
                gameWindow.setActive(true);
                while (lastMove==-1){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {

                    }
                }
                try {
                    game.move(lastMove);
                    lastMove=-1;
                    gameWindow.setActive(false);
                } catch (InvalidMoveException e) {
                    JOptionPane.showConfirmDialog(gameWindow,e.getMessage());
                }
            }else{
                try {
                    sendUpdatetoClient(true);
                    DTOToServer ans = (DTOToServer) in.readObject();
                    game.move(ans.row());
                    gameWindow.updateMatrix(game.getPlayingField());
                    sendUpdatetoClient(false);
                } catch (Exception e) {
                    String msg =e.getMessage();
                    msg.length();
                    //throw new RuntimeException(e);
                }
            }
        }
    }

    private void sendUpdatetoClient(boolean ans) throws IOException {
        out.reset();
        out.writeObject(new DTOToClient(ans,game.gameOver,game.getPlayingField()));
    }

    private void setupConnection() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
            this.clientSocket = serverSocket.accept();
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception ignored) {
        }
    }


    @Override
    public void notify(int selection) {
        lastMove=selection;
    }
}
