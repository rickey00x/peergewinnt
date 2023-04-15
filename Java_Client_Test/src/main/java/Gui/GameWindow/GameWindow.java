package Gui.GameWindow;

import Gui.GameWindow.Matrix.GamePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameWindow extends JPanel {
    public GameWindow() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel sideBar = new SideBar();
        JPanel gamePanel = new GamePanel();
        add(sideBar,BorderLayout.WEST);
        add(gamePanel,BorderLayout.CENTER);
    }
}