package com.mycompany.app;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

    private final static String CRLF = "\r\n";

    private Socket client_socket;

    ClientHandler(Socket client_socket) {
        this.client_socket = client_socket;
    }

    public void run() {
        System.out.println("Incoming request...");
        processRequest();
    }

    private void processRequest() {
        try {
            InputStream input_stream = client_socket.getInputStream();
            DataOutputStream output_stream = new DataOutputStream(client_socket.getOutputStream());
            BufferedReader buffered_reader = new BufferedReader(new InputStreamReader(input_stream));

            HttpRequest request = buildHttpRequest(buffered_reader);
            handleRequest(request,output_stream);

            output_stream.close();
            buffered_reader.close();
            client_socket.close();

        } catch (Exception exception) {
            System.out.println("500 Internal Server Error");
        }
    }

    private void handleRequest(HttpRequest request, DataOutputStream os) throws Exception {
        FileInputStream file;
        String status_line;

        try {
            status_line = "HTTP/1.0 200 OK" + CRLF;
            file = new FileInputStream(request.getFilePath());
        } catch (FileNotFoundException e) {
            status_line = "HTTP/1.0 404 Not Found" + CRLF;
            file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/mycompany/app/www/not_found.html");
        }

        String content_type_line = "Content-Type: text/html" + CRLF;

        os.writeBytes(status_line);
        os.writeBytes(content_type_line);
        os.writeBytes(CRLF);
        sendBytes(file, os);
        file.close();
    }

    private HttpRequest buildHttpRequest(BufferedReader br) throws Exception {
        String request_line = br.readLine();
        StringTokenizer tokens = new StringTokenizer(request_line);

        String method = tokens.nextToken();
        String file_path = System.getProperty("user.dir") + "/src/main/java/com/mycompany/app/www" + tokens.nextToken() + ".html";

        String header_line;
        List<String> headers = new ArrayList<String>();
        while ((header_line = br.readLine()).length() != 0) {
            headers.add(header_line);
        }

        logRequestInfo(headers);
        return new HttpRequest(method, file_path, headers);
    }

    private void logRequestInfo(List headers) {
        for (int i = 0; i < headers.size(); i++) {
            System.out.println(headers.get(i));
        }
        System.out.println("\n\n");
    }

    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

}
