package Gui.StartWindow;
import App.AppStarter;

import javax.swing.*;
import java.awt.*;

public class SelectionWindow extends JPanel {
    public SelectionWindow(AppStarter starter){
        setLayout(new BorderLayout());
        ConnectionButtonPanel buttonPanel = new ConnectionButtonPanel(starter);
        add(buttonPanel,BorderLayout.CENTER);
        JPanel panelW = new JPanel();
        panelW.setSize(new Dimension(200,200));
        add(panelW,BorderLayout.WEST);
        JPanel panelE = new JPanel();
        panelE.setSize(new Dimension(200,200));
        add(panelE,BorderLayout.EAST);
        JPanel panelS = new JPanel();
        panelS.setSize(new Dimension(200,200));
        add(panelS,BorderLayout.SOUTH);
        JPanel panelN = new JPanel();
        panelN.setSize(new Dimension(200,200));
        add(panelN,BorderLayout.NORTH);
        JLabel label = new JLabel("Welcome to PeerGewinnt"); //TODO make pretty for Norman
        label.setPreferredSize(new Dimension(200,200));
        label.setHorizontalAlignment(JLabel.CENTER);
        panelN.add(label);
    }

}
