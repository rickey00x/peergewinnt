package Gui.GameWindow.Matrix;

import Gui.GameWindow.ButtonObserver;
import model.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameMatrixInput extends JPanel {
    MatrixInputButton[] buttons;
    List<ButtonObserver> observerList;

    public GameMatrixInput() {
        this.buttons = new MatrixInputButton[7];
        for (int i = 0; i < buttons.length; i++) {
            var button = new MatrixInputButton(i);
            button.addActionListener(this::buttonClick);
            buttons[i] = button;
            add(button);
        }
        observerList = new ArrayList<>();
    }

    private void buttonClick(ActionEvent actionEvent) {
        MatrixInputButton source = (MatrixInputButton) actionEvent.getSource();
        int selection = source.getId();
        for (var o:observerList
             ) {
            o.notify(selection);
        }
    }

    public void setButtonUsability(boolean option){
        for (var b:buttons
             ) {
            b.setEnabled(option);
        }
    }

    public void subscribe(ButtonObserver observer) {
        observerList.add(observer);
    }
}
