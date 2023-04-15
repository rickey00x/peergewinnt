import Gui.GameWindow.GameWindow;
import Gui.StartWindow.SelectionWindow;
import model.Game;
import model.InvalidMoveException;
import model.Token;
import network.Client;
import network.Server;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Peergewinnt");
        SelectionWindow start = new SelectionWindow();
        mainFrame.add(start);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
