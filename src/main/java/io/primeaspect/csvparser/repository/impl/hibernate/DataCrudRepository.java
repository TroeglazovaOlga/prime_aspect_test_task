package io.primeaspect.csvparser.repository.impl.hibernate;

import io.primeaspect.csvparser.model.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DataCrudRepository extends CrudRepository<Data, Long> {
    List<Data> findAllByName(String name);

    List<Data> findAllByUserName(String name);

    @Transactional
    void deleteAllByName(String name);

    @Transactional
    void deleteAllByUserName(String name);
}