package utb.fai;

public class App {

    public static void main(String[] args) {
        String serverIP = args[0];
        int port = Integer.parseInt(args[1]);

        TelnetClient telnetClient = new TelnetClient(serverIP,port);
        telnetClient.run(); // run telnet client
    }
}
