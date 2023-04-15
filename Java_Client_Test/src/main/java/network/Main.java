package network;

public class Main {
    public static void main(String[] args) {
        int port = 6602;
        Server srv = new Server(port);
        Client clt = new Client(port, "192.168.1.136");

        Thread s = new Thread(srv);
        s.start();

        Thread c = new Thread(clt);
        c.start();


    }
}
