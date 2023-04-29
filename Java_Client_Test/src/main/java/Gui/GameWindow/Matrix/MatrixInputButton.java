package Gui.GameWindow.Matrix;

import javax.swing.*;

public class MatrixInputButton extends JButton {
    final int id;
    public MatrixInputButton(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
