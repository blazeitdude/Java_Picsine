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

            ReadServer readServer = new ReadServer();
            SendServer sendServer = new SendServer();
            readServer.start();
            sendServer.start();
            sendServer.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    private void close() {
        System.out.println("You have left the chat.");
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReadServer extends Thread {
        @Override
        public void run() {
            while (clientSocket.isConnected()) {
                try {
                    String message = in.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    break;
                }

            }
        }
    }

    private class SendServer extends Thread {
        @Override
        public void run() {
            while (clientSocket.isConnected()) {
                try {
                    String message = reader.readLine();
                    if (message.equals("Exit")) {
                        break;
                    }
                    out.write(message + "\n");
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
