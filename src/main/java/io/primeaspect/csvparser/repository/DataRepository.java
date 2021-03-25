package io.primeaspect.csvparser.repository;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public interface DataRepository {
    void saveAll(List<Data> data);
    List<Data> findAllByName(String name);
    List<Data> findAllByUserName(String name);
    List<Data> findAll();
    void deleteAllByName(String name);
    void deleteAllByUserName(String name);
    void deleteAll();
}