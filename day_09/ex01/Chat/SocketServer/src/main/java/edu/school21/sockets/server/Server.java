package edu.school21.sockets.server;

import edu.school21.sockets.conﬁg.SocketsApplicationConﬁg;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private static volatile Server instance;
    private static final String HOST = "localhost";
    public static List<VirtualServer> virtualServerList = new LinkedList<>();
    private int port;
    private Server(int port) {
        this.port = port;
        start();
    }

    private void start() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SocketsApplicationConﬁg.class);
        try (ServerSocket serverSocket = new ServerSocket(port) ) {
            while(true) {
                Socket clientSocket = serverSocket.accept();
                try {
                    virtualServerList.add(new VirtualServer(clientSocket, context));
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } catch  (IOException e) {
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
}
