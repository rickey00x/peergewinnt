package App;

import Gui.GameWindow.GameWindow;
import Gui.StartWindow.SelectionWindow;
import model.Game;
import network.Client2;
import network.Server2;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App implements AppStarter {
    JFrame mainFrame;
    SelectionWindow start;
    GameWindow gameWindow;
    public App() {
        mainFrame = new JFrame("Peergewinnt");
        start = new SelectionWindow((AppStarter) this);
        mainFrame.add(start);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startServer(){
        mainFrame.remove(start);
        gameWindow = new GameWindow();
        JOptionPane.showMessageDialog(mainFrame, "Your ip is: " + getIPAddress() +
                " \n Click ok to start the Server");
        Server2 server = new Server2(gameWindow);
        gameWindow.subscribeToButtons(server);
        Thread serverThread= new Thread(server);
        mainFrame.add(gameWindow);
        mainFrame.pack();
        serverThread.start();

    }

    public void startClient(){
        String serverIP;
        do {
            serverIP = JOptionPane.showInputDialog(mainFrame, "Enter the server ip");
        } while (serverIP != null && !isValidIPAddress(serverIP));
        if (serverIP == null) {
            return;
        }
        mainFrame.remove(start);
        gameWindow = new GameWindow();
        gameWindow.setActive(false);
        mainFrame.add(gameWindow);
        mainFrame.pack();
        Client2 client = new Client2(gameWindow,serverIP);
        gameWindow.subscribeToButtons(client);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    public static boolean isValidIPAddress(String ipAddress) {
        // Regular expression pattern to match IP address
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        Pattern pattern = Pattern.compile(regex);
        if(ipAddress == null)
            return false;
        Matcher matcher = pattern.matcher(ipAddress);

        return matcher.matches();
    }

    private String getIPAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Keine IP vorhanden :(";
    }

}
