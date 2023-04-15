package App;

import Gui.GameWindow.GameWindow;
import Gui.StartWindow.SelectionWindow;

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
        //TODO Add Serverstartshit
        gameWindow = new GameWindow();
        mainFrame.add(gameWindow);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void startClient(){
        mainFrame.remove(start);
        //TODO Add Clientstartshit
        gameWindow = new GameWindow();
        mainFrame.add(gameWindow);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}
