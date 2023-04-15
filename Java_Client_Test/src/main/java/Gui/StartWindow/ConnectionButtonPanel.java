package Gui.StartWindow;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;

public class ConnectionButtonPanel extends JPanel {
    JButton serverButton;
    JButton clientButton;
    public ConnectionButtonPanel(){
        setLayout(new BorderLayout());
        serverButton = new JButton("Hosting");
        clientButton = new JButton("Connection");
        add(serverButton,BorderLayout.NORTH);
        add(clientButton,BorderLayout.SOUTH);
    }
}
