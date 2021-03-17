package io.primeaspect.csvparser.test.base;

import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.jdbc.repository.DataRepositoryJdbc;
import io.primeaspect.csvparser.model.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class DataRepositoryBaseTest {
    private final DataRepository repository;

    public DataRepositoryBaseTest(DataRepository repository) {
        this.repository = repository;
    }

    public void saveTest() {
        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));

        repository.save(requestList);
        List<Data> responseList = repository.getAll();
        Assertions.assertEquals(requestList, responseList);
    }

    public void getTest() {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);

        repository.save(requestList);
        Data response = repository.get(requestData.getName());
        Assertions.assertEquals(requestData, response);
    }
}