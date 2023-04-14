package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class PeergewinntStartScreen extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JButton hostButton, searchButton;
    private JPanel buttonPanel;

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

                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();

                // Handle the client connection
                // ...

                // Close the server socket
                serverSocket.close();
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

                // Receive the list of game rooms
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                multicastSocket.receive(packet);
                String[] gameRooms = new String(buf).split(",");

                // Display the list of game rooms in a dialog box
                String selectedGameRoom = (String) JOptionPane.showInputDialog(
                        PeergewinntStartScreen.this,
                        "Select a game room to join:",
                        "Search for Game Rooms",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        gameRooms,
                        gameRooms[0]);

                // Join the selected game room
                if (selectedGameRoom != null) {
                    // Connect to the selected game room
                    String[] parts = selectedGameRoom.split(":");
                    String host = parts[0];
                    int port = Integer.parseInt(parts[1]);
                    Socket socket = new Socket(host, port);

                    // Handle the connection to the game room
                    // ...
                }

                // Leave the multicast group and close the socket
                multicastSocket.leaveGroup(group);
                multicastSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

        public static void main(String[] args) {
        new PeergewinntStartScreen();
    }
}
