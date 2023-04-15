package Gui;

import Gui.GameWindow.GameWindow;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Panel");
        var main = new GameWindow();
        frame.add(main);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
