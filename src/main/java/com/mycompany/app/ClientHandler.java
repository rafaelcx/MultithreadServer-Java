package com.mycompany.app;

import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        // create input buffer and output buffer
        // wait for input from client and send response back to client
        // close all streams and sockets
    }

}
