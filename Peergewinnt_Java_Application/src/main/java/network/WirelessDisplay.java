package network;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WirelessDisplay {

    public static List<Map<String, String>> findESPs(Boolean test) {
        if(test){
            List<Map<String, String>> espList = new ArrayList<>();
            Map<String, String> esp1 = new HashMap<>();
            esp1.put("name", "Dummy ESP 1");
            esp1.put("ipAddress", "192.168.0.100");
            espList.add(esp1);
            Map<String, String> esp2 = new HashMap<>();
            esp2.put("name", "Dummy ESP 2");
            esp2.put("ipAddress", "192.168.0.101");
            espList.add(esp2);
            return espList;
        }else {
            List<Map<String, String>> espList = new ArrayList<>();

            try {
                JmDNS jmdns = JmDNS.create();
                ServiceInfo[] serviceInfos = jmdns.list("_http._tcp.local.");

                for (int i = 0; i < serviceInfos.length; i++) {
                    ServiceInfo serviceInfo = serviceInfos[i];
                    if (serviceInfo.getName().startsWith("esp8266")) {
                        String ipAddress = serviceInfo.getInet4Addresses()[0].getHostAddress();

                        Map<String, String> espMap = new HashMap<>();
                        espMap.put("name", serviceInfo.getName());
                        espMap.put("ipAddress", ipAddress);

                        espList.add(espMap);
                    }
                }

                jmdns.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return espList;
        }
    }

    public static void connectToESP(String ipAddress, Boolean test) {
        if (test) {
            System.out.println("Connecting to ESP at " + ipAddress);
        } else {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost("http://" + ipAddress + "/blink");
                StringEntity requestEntity = new StringEntity("blink");
                httpPost.setEntity(requestEntity);

                CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity responseEntity = httpResponse.getEntity();
                String responseString = EntityUtils.toString(responseEntity);

                System.out.println("Response from ESP: " + responseString);

                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}