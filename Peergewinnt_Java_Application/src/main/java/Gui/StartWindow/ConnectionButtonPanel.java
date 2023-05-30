package Gui.StartWindow;

import App.AppStarter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.jar.JarEntry;

public class ConnectionButtonPanel extends JPanel {
    JButton serverButton;
    JButton clientButton;
    AppStarter starter;
    public ConnectionButtonPanel(AppStarter starter){
        this.starter=starter;
        setLayout(new GridLayout(2,1,20,20));
        serverButton = new JButton("Hosting");
        serverButton.addActionListener(this::handleServerClick);
        clientButton = new JButton("Connection");
        clientButton.addActionListener(this::handleClientClick);
        add(serverButton);
        add(clientButton);
    }

    private void handleServerClick(ActionEvent actionEvent) {
        starter.startServer();
    }

    private void handleClientClick(ActionEvent actionEvent) {
        starter.startClient();
    }
}
