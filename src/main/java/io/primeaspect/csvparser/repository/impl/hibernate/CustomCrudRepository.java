package io.primeaspect.csvparser.repository.impl.hibernate;

import io.primeaspect.csvparser.model.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCrudRepository extends CrudRepository<Data, Long> {
    List<Data> findAllByName(String name);
}