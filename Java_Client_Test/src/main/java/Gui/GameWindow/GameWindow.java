package Gui.GameWindow;

import Gui.GameWindow.Matrix.GamePanel;
import model.Token;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameWindow extends JPanel {
    public GamePanel gamePanel;
    boolean isActive;
    public GameWindow() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel sideBar = new SideBar();
        gamePanel = new GamePanel();
        add(sideBar,BorderLayout.WEST);
        add(gamePanel,BorderLayout.CENTER);
    }

    public void setActive(boolean option){
        gamePanel.input.setButtonUsability(option);
    }

    public void updateMatrix(Token[][] matrix){
        gamePanel.matrix.updateMatrix(matrix);
    }

    public void subscribeToButtons(ButtonObserver observer){
        gamePanel.input.subscribe(observer);
    }
}
