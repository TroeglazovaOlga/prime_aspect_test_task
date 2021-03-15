package io.primeaspect.csvparser.jdbc.repository;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public interface DataRepository {
    int[] save(List<Data> data);
    Data get(String name);
    List<Data> getAll();
}