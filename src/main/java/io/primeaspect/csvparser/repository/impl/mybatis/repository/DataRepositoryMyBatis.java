package io.primeaspect.csvparser.repository.impl.mybatis.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepositoryMyBatis implements DataRepository {
    private final DataMapper mapper;

    public DataRepositoryMyBatis(DataMapper dataMapper) {
        this.mapper = dataMapper;
    }

    @Override
    public void saveAll(List<Data> data) {
        mapper.createData(data);
    }

    @Override
    public void saveAllByUser(List<Data> data) {

    }

    @Override
    public List<Data> findAllByName(String name) {
        return mapper.findAllByName(name);
    }

    @Override
    public List<Data> findAllByUserName(String name) {
        return null;
    }

    @Override
    public List<Data> findAll() {
        return mapper.findAll();
    }

    @Override
    public int count() {
        return mapper.count() == null? 0 : mapper.count();
    }

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }
}
