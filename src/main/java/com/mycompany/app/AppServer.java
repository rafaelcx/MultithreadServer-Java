package com.mycompany.app;

import java.io.IOException;
import java.net.ServerSocket;

public class AppServer {

    private static final int PORT_NUMBER = 5000;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);

        System.out.println("Server is up and running, listening at port " + PORT_NUMBER);

        while (true) {
            ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    protected void finalize() throws IOException {
        serverSocket.close();
    }

}
