package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.io.*;
import java.net.Socket;
public class VirtualServer extends Thread {
    private UsersService usersService;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private String login;
    private boolean isFinished;

    public VirtualServer(Socket clientSocket, AnnotationConfigApplicationContext context) throws IOException {
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        usersService = context.getBean("usersServiceBean", UsersService.class);
        start();
    }

    @Override
    public void run() {
        try {
            out.write("Hello from Server!\n");
            out.flush();
            while (true) {
                String message = in.readLine();
                switch (message) {
                    case "signIn":
                        if (signIn()) {
                            messaging();
                        }
                        break;
                    case "signUp":
                        if (signUp()) {
                            messaging();
                        }
                        break;
                    case "Exit":
                        break;
                    default:
                        out.write("Invalid command\n");
                        out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            shutdown();
        }
    }

    private boolean signIn() throws IOException {
        out.write("Enter username:\n");
        out.flush();
        login = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();

        if (!usersService.signIn(login, password)) {
            out.write("Invalid login or password\n");
            out.flush();
            return false;
        }
        return true;
    }

    private boolean signUp() throws IOException {
        out.write("Enter username:\n");
        out.flush();
        login = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();

        if (!usersService.signUp(login, password)) {
            out.write("Username '" + login + "' is already taken\n");
            out.flush();
            return false;
        }
        out.write("Successful!\n");
        out.flush();
        return true;
    }

    private void messaging() throws IOException {
        out.write("Start messaging\n");
        out.flush();
        while (true) {
            String message = in.readLine();
            if (message.equals("Exit")) {
                break;
            }
            for (VirtualServer vs : Server.virtualServerList) {
                usersService.sendMessage(login, message);
                vs.out.write(login + ": " + message + "\n");
                vs.out.flush();
            }
        }
    }

    private void shutdown() {
        try {
            if(!isFinished) {
                isFinished = true;
                clientSocket.close();
                in.close();
                out.close();
                for (VirtualServer vs : Server.virtualServerList) {
                    if(vs.equals(this))
                        vs.interrupt();
                    Server.virtualServerList.remove(this);
                }
            }
        } catch (IOException ignored) {}
    }
}
