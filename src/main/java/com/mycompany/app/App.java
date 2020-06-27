package com.mycompany.app;

import java.io.IOException;
import java.net.ServerSocket;

public class App {

    private static final int PORT_NUMBER = 5000;
    private static ServerSocket serverSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;

    public static void main( String[] args ) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);

        while (true) {
            clientHandler = new ClientHandler(serverSocket.accept());
            thread = new Thread(clientHandler);
            thread.start();
        }
    }

    protected void finalize() throws IOException {
        serverSocket.close();
    }

}
