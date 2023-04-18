package App;

import Gui.GameWindow.GameWindow;
import Gui.StartWindow.SelectionWindow;
import model.Game;
import network.Client2;
import network.Server2;

import javax.swing.*;

public class App implements AppStarter {
    JFrame mainFrame;
    SelectionWindow start;
    GameWindow gameWindow;
    public App() {
        mainFrame = new JFrame("Peergewinnt");
        start = new SelectionWindow((AppStarter) this);
        mainFrame.add(start);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startServer(){
        mainFrame.remove(start);
        gameWindow = new GameWindow();
        Server2 server = new Server2(gameWindow);
        gameWindow.subscribeToButtons(server);
        Thread serverThread= new Thread(server);
        mainFrame.add(gameWindow);
        mainFrame.pack();
        serverThread.start();
    }

    public void startClient(){
        mainFrame.remove(start);
        gameWindow = new GameWindow();
        gameWindow.setActive(false);
        mainFrame.add(gameWindow);
        mainFrame.pack();
        Client2 client = new Client2(gameWindow);
        gameWindow.subscribeToButtons(client);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

}
