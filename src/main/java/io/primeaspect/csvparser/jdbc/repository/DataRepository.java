package io.primeaspect.csvparser.jdbc.repository;

import io.primeaspect.csvparser.model.Data;

import java.util.List;

public interface DataRepository {
    int[] save(List<Data> data);
    Data findByName(String name);
}