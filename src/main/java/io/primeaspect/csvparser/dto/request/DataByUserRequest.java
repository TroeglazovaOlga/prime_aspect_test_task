package io.primeaspect.csvparser.dto.request;

import io.primeaspect.csvparser.model.User;

import java.util.Objects;

public class DataByUserRequest {
    private User user;
    private String content;

    public DataByUserRequest() {}

    public DataByUserRequest(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataByUserRequest that = (DataByUserRequest) o;
        return Objects.equals(content, that.content) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, user);
    }
}
