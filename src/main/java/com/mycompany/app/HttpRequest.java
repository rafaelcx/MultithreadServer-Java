package com.mycompany.app;

import java.util.List;

public class HttpRequest {

    private String method;
    private String file_path;
    private String content_type;
    private List headers;

    HttpRequest(String method, String file_path, String content_type, List headers) {
        this.method = method;
        this.file_path = file_path;
        this.content_type = content_type;
        this.headers = headers;
    }

    public String getMethod() {
        return this.method;
    }

    public String getFilePath() {
        return this.file_path;
    }

    public String getContentType() {
        return this.content_type;
    }

    public List getHeaders() {
        return this.headers;
    }

}
