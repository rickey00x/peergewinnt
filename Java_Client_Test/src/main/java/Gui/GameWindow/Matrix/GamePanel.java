package Gui.GameWindow.Matrix;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    GameMatrixInput input;
    GameMatrix matrix;

    public GamePanel() {
        setLayout(new BorderLayout());
        input = new GameMatrixInput();
        add(input,BorderLayout.NORTH);
        matrix = new GameMatrix();
        add(matrix,BorderLayout.CENTER);

    }
}
