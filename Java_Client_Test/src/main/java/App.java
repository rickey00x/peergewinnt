import Gui.GameWindow.GameWindow;
import model.Game;
import model.InvalidMoveException;
import model.Token;
import network.Client;
import network.Server;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Panel");
        GameWindow mainWindow = new GameWindow();
        frame.add(mainWindow);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game("p1","p2");
        try {
            game.move(3);
            game.move(3);
            game.move(2);
            game.move(6);
            game.move(3);
            game.move(3);
            game.move(2);
            game.move(6);
            game.move(3);
            game.move(3);
            game.move(2);
            game.move(6);
            game.move(34);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        mainWindow.gamePanel.matrix.updateMatrix(game.getPlayingField());
    }


}
