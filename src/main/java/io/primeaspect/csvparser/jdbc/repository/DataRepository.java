package io.primeaspect.csvparser.jdbc.repository;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public interface DataRepository {
    int save(Data data);
    List<Data> findAll();
}