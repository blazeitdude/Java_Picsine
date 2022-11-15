package edu.school21.sockets.server;

import edu.school21.sockets.conﬁg.SocketsApplicationConﬁg;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static volatile Server instance;
    private static final String HOST = "localhost";
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private int port;
    private Server(int port) {
        this.port = port;
        start();
        communicate();
    }

    private void start() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Server getInstance(int port) {
        Server localInstance = instance;
        if (localInstance == null) {
            synchronized (Server.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Server(port);
                }
            }
        }
        return localInstance;
    }

    private void close() {
        System.out.println("connection closed");
        try {
            clientSocket.close();
            serverSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void communicate() {
        AnnotationConfigApplicationContext context = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            out.write("Hello from Server!\n");
            out.flush();
            context = new AnnotationConfigApplicationContext(SocketsApplicationConﬁg.class);
            UsersService usersService = context.getBean("usersServiceBean", UsersService.class);
            while (clientSocket.isConnected()) {
                if (in.readLine().equals("signUp")) {
                    out.write("Enter username:\n");
                    out.flush();
                    String login = in.readLine();
                    out.write("Enter password:\n");
                    out.flush();
                    String password = in.readLine();

                    if (usersService.signUp(login, password)) {
                        out.write("Successful!\n");
                        out.flush();
                        break;
                    } else  {
                        out.write("Username '" + login + "' is already taken\n");
                        out.flush();
                    }
                } else {
                    out.write("enter 'signUp' to register\n");
                    out.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (context != null)
                context.close();
            close();
        }
    }
}
