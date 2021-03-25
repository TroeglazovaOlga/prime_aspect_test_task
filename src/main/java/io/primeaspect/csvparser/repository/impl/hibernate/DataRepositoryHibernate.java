package io.primeaspect.csvparser.repository.impl.hibernate;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class DataRepositoryHibernate implements DataRepository {
    private final DataCrudRepository repository;

    public DataRepositoryHibernate(DataCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveAll(List<Data> data) {
        repository.saveAll(data);
    }

    @Override
    public List<Data> findAllByName(String name) {
        return repository.findAllByName(name);
    }

    @Override
    public List<Data> findAllByUserName(String name) {
        return repository.findAllByUserName(name);
    }

    @Override
    public List<Data> findAll() {
        return (List<Data>) repository.findAll();
    }

    @Override
    public void deleteAllByName(String name) {
        this.repository.deleteAllByName(name);
    }

    @Override
    public void deleteAllByUserName(String name) {
        this.repository.deleteAllByUserName(name);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
