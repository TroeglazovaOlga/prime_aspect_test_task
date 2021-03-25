package io.primeaspect.csvparser.repository.impl.mybatis.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapperMyBatis;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepositoryMyBatis implements DataRepository {
    private final DataMapperMyBatis mapper;

    public DataRepositoryMyBatis(DataMapperMyBatis dataMapper) {
        this.mapper = dataMapper;
    }

    @Override
    public void saveAll(List<Data> data) {
        User user = data.get(0).getUser();
        if (mapper.findUserByName(user.getName()) == null) {
            mapper.createUser(user);
        }
        mapper.createData(data, user);
    }

    @Override
    public List<Data> findAllByName(String name) {
        return mapper.findAllByName(name);
    }

    @Override
    public List<Data> findAllByUserName(String name) {
        return mapper.findAllByUserName(name);
    }

    @Override
    public List<Data> findAll() {
        return mapper.findAll();
    }

    @Override
    public void deleteAllByName(String name) {
        mapper.deleteByName(name);
    }

    @Override
    public void deleteAllByUserName(String name) {
        mapper.deleteByUserName(name);
    }

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }
}
