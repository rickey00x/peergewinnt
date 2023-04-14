package org.example;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EspDiscovery {
    public static void main(String[] args) {
        try {
            JmDNS jmdns = JmDNS.create();
            ServiceInfo[] serviceInfos = jmdns.list("_http._tcp.local.");

            System.out.println("Found " + serviceInfos.length + " ESP(s) on the network.");

            for (int i = 0; i < serviceInfos.length; i++) {
                ServiceInfo serviceInfo = serviceInfos[i];
                if (serviceInfo.getName().startsWith("esp8266")) {
                    String ipAddress = serviceInfo.getInet4Addresses()[0].getHostAddress();
                    System.out.println((i + 1) + ". " + serviceInfo.getName() + " at " + ipAddress);
                }
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of the ESP to connect to: ");
            int selection = scanner.nextInt();

            ServiceInfo selectedService = serviceInfos[selection - 1];
            String ipAddress = selectedService.getInet4Addresses()[0].getHostAddress();
            System.out.println("Selected " + selectedService.getName() + " at " + ipAddress);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://" + ipAddress + "/blink");
            StringEntity requestEntity = new StringEntity("blink");
            httpPost.setEntity(requestEntity);

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(responseEntity);

            System.out.println("Response from ESP: " + responseString);

            jmdns.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
