package Gui.GameWindow.Matrix;

import Gui.Renderer;
import model.Token;

import javax.swing.*;
import java.awt.*;

public class GameMatrix extends JPanel implements Renderer {
    JLabel[] labels;

    final ImageIcon red = new ImageIcon(new ImageIcon("Java_Client_Test/Images/red.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    final ImageIcon blue = new ImageIcon(new ImageIcon("Java_Client_Test/Images/blue.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    final ImageIcon empty = new ImageIcon(new ImageIcon("Java_Client_Test/Images/empty.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

    public GameMatrix() {
        setLayout(new GridLayout(6,7));
        labels = new JLabel[42];
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel();
            label.setIcon(empty);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labels[i] = label;
            add(label);
        }

    }
    public void updateMatrix(Token[][] matrix){
        int maxRow = matrix[0].length;
        int maxCol = matrix.length;
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                int index1D = row+maxRow*col;
                switch (matrix[col][row]) {
                    case X -> labels[index1D].setIcon(red);
                    case O -> labels[index1D].setIcon(blue);
                    default -> labels[index1D].setIcon(empty);
                }
            }
        }
    }
}
