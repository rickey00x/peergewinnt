package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class PeergewinntStartScreen extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JButton hostButton, searchButton;
    private JPanel buttonPanel;
    private ArrayList<GameRoom> gameRooms = new ArrayList<GameRoom>();

    public PeergewinntStartScreen() {
        super("Peergewinnt");

        // Create a label for the title
        titleLabel = new JLabel("<html><b>Welcome to Peergewinnt</b></html>");
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 36));

        // Create buttons for hosting and searching for games
        hostButton = new JButton("Host game");
        hostButton.addActionListener(this);
        searchButton = new JButton("Search for games");
        searchButton.addActionListener(this);

        // Create a panel to hold the buttons
        buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.add(hostButton);
        buttonPanel.add(searchButton);

        // Set the preferred size of the buttons to be the same as the label
        Dimension buttonSize = new Dimension(titleLabel.getPreferredSize().width,
                titleLabel.getPreferredSize().height);
        hostButton.setPreferredSize(buttonSize);
        searchButton.setPreferredSize(buttonSize);

        // Use a GridBagLayout to center the label
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 50, 0);
        centerPanel.add(titleLabel, c);

        // Add the components to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and visibility of the frame
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hostButton) {
            // Code to create a game room goes here
            try {
                // Create a server socket on a random port
                ServerSocket serverSocket = new ServerSocket(0);

                // Print the port number to the console
                System.out.println("Game room created on port " + serverSocket.getLocalPort());

                // Add the game room to the list
                GameRoom gameRoom = new GameRoom(InetAddress.getLocalHost().getHostAddress(),
                        serverSocket.getLocalPort());
                gameRooms.add(gameRoom);

                // Start a thread to handle incoming connections
                Thread connectionThread = new Thread(new ConnectionHandler(serverSocket));
                connectionThread.start();

                // Update the title label to show the game room address and port
                titleLabel.setText("<html><b>Game room hosted on "
                        + gameRoom.getAddress() + ":" + gameRoom.getPort() + "</b></html>");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == searchButton) {
            // Code to display a list of available game rooms goes here
            try {
                // Create a multicast socket to listen for game rooms
                MulticastSocket multicastSocket = new MulticastSocket(4446);
                InetAddress group = InetAddress.getByName("230.0.0.0");
                multicastSocket.joinGroup(group);

                // Send a discovery message to the multicast group
                byte[] buf = "DISCOVER".getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                multicastSocket.send(packet);
                // Wait for responses from game rooms
                byte[] responseBuf = new byte[256];
                while (true) {
                    DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length);
                    multicastSocket.receive(responsePacket);
                    String response = new String(responsePacket.getData(), 0, responsePacket.getLength());

                    // Parse the response and add the game room to the list
                    String[] parts = response.split(":");
                    if (parts.length == 2) {
                        String address = parts[0];
                        int port = Integer.parseInt(parts[1]);
                        GameRoom gameRoom = new GameRoom(address, port);
                        gameRooms.add(gameRoom);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Create a dialog to display the available game rooms
            JDialog dialog = new JDialog(this, "Available game rooms", true);
            JPanel panel = new JPanel(new GridLayout(gameRooms.size(), 1, 0, 10));
            for (GameRoom gameRoom : gameRooms) {
                JButton button = new JButton(gameRoom.getAddress() + ":" + gameRoom.getPort());
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Code to connect to the selected game room goes here
                        try {
                            Socket socket = new Socket(gameRoom.getAddress(), gameRoom.getPort());
                            System.out.println("Connected to game room at " + socket.getRemoteSocketAddress());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                panel.add(button);
            }
            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    private static class GameRoom {
        private String address;
        private int port;

        public GameRoom(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public String getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }
    }

    private static class ConnectionHandler implements Runnable {
        private ServerSocket serverSocket;

        public ConnectionHandler(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public void run() {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Connection from " + socket.getRemoteSocketAddress());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PeergewinntStartScreen startScreen = new PeergewinntStartScreen();
    }
}
