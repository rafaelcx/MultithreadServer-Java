package com.mycompany.app;

import java.util.List;

public class HttpRequest {

    private String method;
    private String file_path;
    private List headers;

    HttpRequest(String method, String file_path, List headers) {
        this.method = method;
        this.file_path = file_path;
        this.headers = headers;
    }

    public String getMethod() {
        return this.method;
    }

    public String getFilePath() {
        return this.file_path;
    }

    public List getHeaders() {
        return this.headers;
    }

}
