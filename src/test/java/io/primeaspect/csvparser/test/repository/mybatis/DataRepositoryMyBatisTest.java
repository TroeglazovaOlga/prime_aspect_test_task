package io.primeaspect.csvparser.test.repository.mybatis;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.impl.jdbc.DataRepositoryJdbc;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.repository.impl.mybatis.repository.DataRepositoryMyBatis;
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

@SpringBootTest(classes = {TestConfiguration.class, DataRepositoryMyBatis.class, DataMapper.class})
@ComponentScan(basePackages = "io.primeaspect.csvparser")
@MapperScan("io.primeaspect.csvparser.repository.impl.mybatis.mapper")
public class DataRepositoryMyBatisTest {
    @Autowired
    private DataRepositoryJdbc repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));

        repository.saveAll(requestList);
        List<Data> responseList = repository.findAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllByNameTest() {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);

        repository.saveAll(requestList);
        List<Data> response = repository.findAllByName(requestData.getName());
        Assertions.assertEquals(requestList, response);
    }

    @Test
    public void findAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));
        repository.saveAll(requestList);

        List<Data> response = repository.findAll();
        Assertions.assertEquals(requestList, response);
    }

    @Test
    public void deleteAllByName() {
        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        repository.saveAll(requestList);

        String request = "id";

        repository.deleteAllByName(request);
        List<Data> resultList = repository.findAllByName(request);
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    public void deleteAll() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));
        repository.saveAll(requestList);

        repository.deleteAll();
        List<Data> resultList = repository.findAll();
        Assertions.assertTrue(resultList.isEmpty());
    }
}