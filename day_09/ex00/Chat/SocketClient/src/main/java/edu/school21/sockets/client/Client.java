package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public Client(int port) {
        start(port);
    }

    private void start(int port) {
        try {
			clientSocket = new Socket(HOST, port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (clientSocket.isConnected()) {
                String message = in.readLine();
                System.out.println(message);
                if (message.equals("Successful!")) {
                    break;
                }
                message = reader.readLine();
                if (message.equals("Exit")) {
                    break;
                }
                out.write(message + "\n");
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    private void close() {
        System.out.println("connection closed");
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
