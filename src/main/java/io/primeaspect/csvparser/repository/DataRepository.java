package io.primeaspect.csvparser.repository;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public interface DataRepository {
    void saveAll(List<Data> data);
    List<Data> findAllByName(String name);
    List<Data> findAll();
    int count();
    void deleteAll();
}
