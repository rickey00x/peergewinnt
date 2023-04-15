package Gui.StartWindow;

import javax.swing.*;
import java.awt.*;

public class SelectionWindow extends JPanel {
    public SelectionWindow(){
        setLayout(new BorderLayout());
        ConnectionButtonPanel buttonPanel = new ConnectionButtonPanel();
        add(buttonPanel,BorderLayout.CENTER);
        add(new JLabel("PeerGewinnt"),BorderLayout.NORTH);
    }
}
