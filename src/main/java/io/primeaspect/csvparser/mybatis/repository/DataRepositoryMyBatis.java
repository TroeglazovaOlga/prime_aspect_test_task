package io.primeaspect.csvparser.mybatis.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.repository.DataRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository("MyBatis")
public class DataRepositoryMyBatis implements DataRepository {
    private final DataMapper mapper;

    public DataRepositoryMyBatis(DataMapper dataMapper){
        this.mapper = dataMapper;
    }

    @Override
    public void save(List<Data> data) {
        mapper.save(data);
    }

    @Override
    public Data get(String name) {
        return mapper.get(name);
    }

    @Override
    public List<Data> getAll() {
        return mapper.getAll();
    }
}