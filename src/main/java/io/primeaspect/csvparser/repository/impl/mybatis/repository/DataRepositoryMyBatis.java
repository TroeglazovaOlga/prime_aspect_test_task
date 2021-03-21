package io.primeaspect.csvparser.repository.impl.mybatis.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepositoryMyBatis implements DataRepository {
    private final DataMapper mapper;

    public DataRepositoryMyBatis(DataMapper dataMapper){
        this.mapper = dataMapper;
    }

    @Override
    public void save(List<Data> data) {
        mapper.createData(data);
    }

    @Override
    public List<Data> getByName(String name) {
        return mapper.getByName(name);
    }

    @Override
    public List<Data> getAll() {
        return mapper.getAll();
    }

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }
}
