package io.primeaspect.csvparser.repository.impl.mybatis.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.repository.DataRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository("myBatis")
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
    public Data get(String name) {
        return mapper.get(name);
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
