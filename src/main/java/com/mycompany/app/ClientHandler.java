package com.mycompany.app;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

    private final static String CRLF = "\r\n";

    private Socket client_socket;

    ClientHandler(Socket clientSocket) {
        this.client_socket = clientSocket;
    }

    public void run() {
        try {
            System.out.println("Incoming request...");
            processRequest();
        } catch (Throwable $exception) {
            System.out.println($exception.getMessage());
        }
    }

    private void processRequest() throws Exception {
        InputStream is = client_socket.getInputStream();
        DataOutputStream os = new DataOutputStream(client_socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String requestLine = br.readLine();

        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken();  // skip the method
        String fileName = tokens.nextToken();

        fileName = System.getProperty("user.dir") + "/src/main/java/com/mycompany/app/www" + fileName + ".html";

        FileInputStream fis = null ;
        boolean fileExists = true ;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            fileExists = false ;
        }

        // General info about the request that can be seen on the IO
        System.out.println(requestLine);
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }


        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;

        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-Type: " +
                    contentType(fileName) + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content-Type: text/html" + CRLF;
            entityBody = "<HTML>" +
                    "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                    "<BODY>Not Found</BODY></HTML>";
        }

        os.writeBytes(statusLine);
        os.writeBytes(contentTypeLine);
        os.writeBytes(CRLF);

        // Send the entity body.
        if (fileExists) {
            sendBytes(fis, os);
            fis.close();
        } else {
            os.writeBytes(entityBody) ;
        }

        os.close();
        br.close();
        client_socket.close();
    }

    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

    private static String contentType(String fileName) {
        if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }

        return "application/octet-stream" ;
    }

}
