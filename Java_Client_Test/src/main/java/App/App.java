package App;

import Gui.GameWindow.GameWindow;
import Gui.StartWindow.SelectionWindow;
import model.Game;
import network.WirelessDisplay;
import network.Client2;
import network.Server2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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

    public void startServer() {
        mainFrame.remove(start);
        gameWindow = new GameWindow();
        int result = JOptionPane.showConfirmDialog(mainFrame, "Do you want to connect a Wireless Display to the Server ?");
        if (result == JOptionPane.YES_OPTION) {
            SwingWorker<List<Map<String, String>>, Void> worker = new SwingWorker<List<Map<String, String>>, Void>() {
                private JDialog searchingDialog;

                @Override
                protected List<Map<String, String>> doInBackground() throws Exception {
                    searchingDialog = new JDialog(mainFrame, "Searching...");
                    searchingDialog.setLocationRelativeTo(mainFrame);
                    searchingDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                    JProgressBar progressBar = new JProgressBar();
                    progressBar.setIndeterminate(true);
                    searchingDialog.add(progressBar);
                    searchingDialog.pack();
                    searchingDialog.setVisible(true);

                    return WirelessDisplay.findESPs(false);
                }

                @Override
                protected void done() {
                    searchingDialog.dispose();
                    try {
                        List<Map<String, String>> espList = get();
                        if (espList.isEmpty()) {
                            JOptionPane.showMessageDialog(mainFrame, "No Displays Found", "ESP Discovery App", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            String[] espNames = espList.stream().map(espMap -> espMap.get("name")).toArray(String[]::new);
                            JComboBox<String> espComboBox = new JComboBox<>(espNames);

                            JButton connectButton = new JButton("Connect");

                            JOptionPane optionPane = new JOptionPane();
                            JPanel panel = new JPanel(new FlowLayout());
                            optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);

                            JDialog dialog = optionPane.createDialog(mainFrame, "ESP Discovery App");
                            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            connectButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int selectedIndex = espComboBox.getSelectedIndex();
                                    Map<String, String> selectedESP = espList.get(selectedIndex);
                                    String espIp = selectedESP.get("ipAddress");
                                    WirelessDisplay.connectToESP(espIp, false);
                                    dialog.dispose();
                                    startServerWithEspIp(espIp);
                                }
                            });
                            panel.add(espComboBox);
                            panel.add(connectButton);
                            optionPane.setMessage(panel);
                            dialog.setVisible(true);
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            worker.execute();
        } else {
            startServerWithEspIp(null);
        }
    }

    private void startServerWithEspIp(String espIp) {
        JOptionPane.showMessageDialog(mainFrame, "Your ip is: " + getIPAddress() + " \n Click ok to start the Server");
        Server2 server = new Server2(gameWindow, espIp);
        gameWindow.subscribeToButtons(server);
        Thread serverThread = new Thread(server);
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
