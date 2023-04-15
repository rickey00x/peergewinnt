package Gui.GameWindow.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMatrix extends JPanel {
    JLabel[] labels;
    public GameMatrix() {
        setLayout(new GridLayout(7,6));
        labels = new JLabel[42];
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel("O");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labels[i] = label;
            add(label);
        }

    }
    public void setLabel(int col, int row,String text){
        labels[col*6+row].setText(text);
    }
}
