package network;

public class Main {
    public static void main(String[] args) {
        int port = 6602;
        Server srv = new Server(port);
        Client clt = new Client(port, "127.0.0.1");

        Thread s = new Thread(srv);
        s.start();

        Thread c = new Thread(clt);
        c.start();


    }
}
