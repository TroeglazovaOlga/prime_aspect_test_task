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
import java.util.*;

public class DataRepositoryTest {
    private static DataRepository repository;

    @BeforeAll
    public static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        repository = new DataRepositoryImpl(jdbcTemplate);

        List<Data> testContent = new LinkedList<>();
        testContent.add(new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;"));
        repository.save(testContent);
    }

    @Test
    public void saveTest() {
        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));

        int[] result = repository.save(list);
        for (int i : result) {
            Assertions.assertEquals(i, 1);
        }
    }

    @Test
    public void getTest() {
        Data data = repository.get("id");
        Assertions.assertEquals(data, new Data("id", "0;1;2;3;"));
    }
}
