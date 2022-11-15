package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

public class Main {
    private static final String FLAG_PORT = "--server-port=";
    private static int port;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(FLAG_PORT)) {
            System.out.println("Usage: java -jar target/socket-client.jar --server-port=PORT_NUMBER");
            System.exit(0);
        }
        try {
            port = Integer.parseInt(args[0].substring(FLAG_PORT.length()));
            new Client(port);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
