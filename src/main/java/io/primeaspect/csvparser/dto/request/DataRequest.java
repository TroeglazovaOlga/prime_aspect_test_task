package io.primeaspect.csvparser.dto.request;

import io.primeaspect.csvparser.model.User;

import java.util.Objects;

public class DataRequest {
    private User user;
    private String content;

    public DataRequest() {
    }

    public DataRequest(String content) {
        this.content = content;
    }

    public DataRequest(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRequest that = (DataRequest) o;
        return Objects.equals(user, that.user) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, content);
    }
}