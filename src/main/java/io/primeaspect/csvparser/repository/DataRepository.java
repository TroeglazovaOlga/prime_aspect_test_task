package io.primeaspect.csvparser.repository;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public interface DataRepository {
    void save(List<Data> data);
    Data get(String name);
    List<Data> getAll();
}