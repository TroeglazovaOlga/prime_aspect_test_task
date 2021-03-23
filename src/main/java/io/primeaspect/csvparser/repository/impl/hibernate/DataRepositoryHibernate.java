package io.primeaspect.csvparser.repository.impl.hibernate;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class DataRepositoryHibernate implements DataRepository {
    private final CustomCrudRepository repository;

    public DataRepositoryHibernate(CustomCrudRepository repository) {
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
    public List<Data> findAll() {
        return (List<Data>) repository.findAll();
    }

    @Override
    public int count() {
        return (int) repository.count();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
