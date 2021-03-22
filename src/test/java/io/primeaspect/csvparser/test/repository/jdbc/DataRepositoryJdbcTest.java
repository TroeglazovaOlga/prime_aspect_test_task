package io.primeaspect.csvparser.test.repository.jdbc;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.jdbc.DataRepositoryJdbc;
import io.primeaspect.csvparser.test.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {TestConfiguration.class, DataRepositoryJdbc.class})
@ComponentScan(basePackages = "io.primeaspect.csvparser")
public class DataRepositoryJdbcTest {

    @Autowired
    @Qualifier("dataRepositoryJdbc")
    private DataRepository repository;

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
        Assertions.assertEquals(requestList.size(), repository.count());
    }

    @Test
    public void deleteAll() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));
        repository.saveAll(requestList);

        repository.deleteAll();
        int countAfterDelete = repository.count();
        Assertions.assertEquals(countAfterDelete, 0);
    }
}
