package io.primeaspect.csvparser.model;

public class DataRequest {
    private String content;

    public DataRequest() {
    }

    public DataRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
