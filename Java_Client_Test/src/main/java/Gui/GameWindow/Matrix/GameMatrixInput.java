package Gui.GameWindow.Matrix;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameMatrixInput extends JPanel {
    MatrixInputButton[] buttons;

    public GameMatrixInput() {
        this.buttons = new MatrixInputButton[7];
        for (int i = 0; i < buttons.length; i++) {
            var button = new MatrixInputButton(i);
            button.addActionListener(this::buttonClick);
            buttons[i] = button;
            add(button);
        }
    }

    private void buttonClick(ActionEvent actionEvent) {
        MatrixInputButton source = (MatrixInputButton) actionEvent.getSource();
    }
}
