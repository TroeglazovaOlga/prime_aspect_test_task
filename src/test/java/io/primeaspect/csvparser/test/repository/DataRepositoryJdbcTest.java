package io.primeaspect.csvparser.test.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.jdbc.DataRepositoryJdbc;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.test.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {TestConfiguration.class, DataRepositoryJdbc.class, DataMapper.class})
@ComponentScan(basePackages = "io.primeaspect.csvparser")
@MapperScan("io.primeaspect.csvparser.repository.impl.mybatis.mapper")
public class DataRepositoryJdbcTest {
    @Autowired
    private DataRepository repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));

        repository.save(requestList);
        List<Data> responseList = repository.getAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void getTest() {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);

        repository.save(requestList);
        Data response = repository.get(requestData.getName());
        Assertions.assertEquals(requestData, response);
    }
}
