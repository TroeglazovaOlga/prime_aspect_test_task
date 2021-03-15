package io.primeaspect.csvparser.test;

import io.primeaspect.csvparser.jdbc.repository.DataRepository;
import io.primeaspect.csvparser.jdbc.repository.DataRepositoryImpl;
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

public class DataRepositoryTest {
    private static DataRepository repository;

    @BeforeAll
    public static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        repository = new DataRepositoryImpl(jdbcTemplate);
    }

    @Test
    public void saveTest() {
        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));

        repository.save(requestList);
        Assertions.assertEquals(requestList, repository.getAll());
    }

    @Test
    public void getTest() {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);

        repository.save(requestList);
        Assertions.assertEquals(requestData, repository.get(requestData.getName()));
    }
}