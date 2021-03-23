package io.primeaspect.csvparser.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="data")
public class Data implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String content;

    public Data() {
    }

    public Data(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Data data = (Data) o;
        return Objects.equals(name, data.name) && Objects.equals(content, data.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
