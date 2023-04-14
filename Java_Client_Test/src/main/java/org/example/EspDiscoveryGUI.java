package org.example;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceInfo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class EspDiscoveryGUI {
    private JFrame frame;
    private JComboBox<String> espComboBox;
    private JButton addLedButton;
    private JButton connectButton;

    private JmDNS jmdns;
    private ServiceInfo[] serviceInfos;

    public EspDiscoveryGUI() {
        initialize();
    }

    private JProgressBar progressBar;


    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(220, 50, 150, 30);
        progressBar.setVisible(false);
        frame.getContentPane().add(progressBar);

        espComboBox = new JComboBox<>();
        espComboBox.setBounds(10, 10, 200, 20);
        frame.getContentPane().add(espComboBox);

        addLedButton = new JButton("Add LED display");
        addLedButton.setBounds(10, 50, 150, 30);
        frame.getContentPane().add(addLedButton);

        connectButton = new JButton("Connect");
        connectButton.setBounds(220, 10, 150, 30);
        connectButton.setEnabled(false);
        frame.getContentPane().add(connectButton);

        addLedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                espComboBox.removeAllItems();
                connectButton.setEnabled(false);
                progressBar.setVisible(true);
                discoverEsps();
            }
        });



        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToEsp();
            }
        });

        frame.setVisible(true);

        discoverEsps();
    }

    private void discoverEsps() {
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            jmdns.addServiceListener("_http._tcp.local.", new ServiceListener() {
                @Override
                public void serviceAdded(ServiceEvent event) {
                    System.out.println("Service added: " + event.getInfo().getName());
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {
                    System.out.println("Service removed: " + event.getInfo().getName());
                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                    System.out.println("Service resolved: " + event.getInfo().getName());
                    serviceInfos = jmdns.list("_http._tcp.local.");
                    if (serviceInfos != null && serviceInfos.length > 0) {
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setIndeterminate(false);
                            progressBar.setValue(progressBar.getMaximum());
                            espComboBox.removeAllItems();
                            for (ServiceInfo serviceInfo : serviceInfos) {
                                if (serviceInfo.getName().startsWith("esp8266")) {
                                    espComboBox.addItem(serviceInfo.getName());
                                }
                            }
                            if (espComboBox.getItemCount() > 0) {
                                connectButton.setEnabled(true);
                            } else {
                                connectButton.setEnabled(false);
                            }
                        });
                    }
                }
            });

            progressBar.setIndeterminate(true);
            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (serviceInfos == null || serviceInfos.length == 0) {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setIndeterminate(false);
                        JOptionPane.showMessageDialog(frame, "No ESPs found.");
                    });
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToEsp() {
        String selectedEspName = (String) espComboBox.getSelectedItem();
        if (selectedEspName != null) {
            for (ServiceInfo serviceInfo : serviceInfos) {
                if (serviceInfo.getName().equals(selectedEspName)) {
                    String ipAddress = serviceInfo.getInet4Addresses()[0].getHostAddress();
                    String url = "http://" + ipAddress + "/blink";

                    // create an HttpPost request with the URL
                    HttpPost httpPost = new HttpPost(url);

                    // set the request body as a JSON object
                    String requestBody = "{ \"blink\": true }";
                    StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
                    httpPost.setEntity(entity);

                    // create an HttpClient and execute the request
                    try (CloseableHttpClient httpClient = HttpClients.createDefault();
                         CloseableHttpResponse response = httpClient.execute(httpPost)) {

                        // check if the response was successful
                        if (response.getStatusLine().getStatusCode() == 200) {
                            System.out.println("Successfully sent blink request to " + ipAddress);
                        } else {
                            System.out.println("Failed to send blink request to " + ipAddress);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    public static void main(String[] args) {
        EspDiscoveryGUI gui = new EspDiscoveryGUI();
    }
}
