package io.primeaspect.csvparser.repository.impl.jdbc.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.jdbc.mapper.DataMapperJdbc;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepositoryJdbc implements DataRepository {
    private final DataMapperJdbc jdbcMapper;

    public DataRepositoryJdbc(DataMapperJdbc jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public void saveAll(List<Data> dataList) {
        User user = dataList.get(0).getUser();
        if (jdbcMapper.findUserByName(user.getName()).isEmpty()) {
            jdbcMapper.saveUser(dataList.get(0).getUser());
            List<User> usersByName = jdbcMapper.findUserByName(user.getName());
            long id = usersByName.get(0).getId();
            dataList.forEach(data -> data.getUser().setId(id));
        }
        jdbcMapper.saveDataList(dataList);
    }

    @Override
    public List<Data> findAllByName(String name) {
        return jdbcMapper.findAllByName(name);
    }

    @Override
    public List<Data> findAllByUserName(String name) {
        return jdbcMapper.findAllByUserName(name);
    }

    @Override
    public List<Data> findAll() {
        return jdbcMapper.findAll();
    }

    @Override
    public void deleteAllByName(String name) {
        jdbcMapper.deleteAllByName(name);
    }

    @Override
    public void deleteAllByUserName(String name) {
        List<User> users = jdbcMapper.findUserByName(name);
        if (!users.isEmpty()) {
            long id = users.get(0).getId();
            jdbcMapper.deleteAllByUserId(id);
        }
    }

    @Override
    public void deleteAll() {
        jdbcMapper.deleteData();
        jdbcMapper.deleteUsers();
    }
}