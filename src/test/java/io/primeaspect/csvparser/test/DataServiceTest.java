package io.primeaspect.csvparser.test;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.jdbc.repository.DataRepository;
import io.primeaspect.csvparser.jdbc.repository.DataRepositoryImpl;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.service.DataService;
import io.primeaspect.csvparser.service.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataServiceTest {
    private static DataService service;

    @BeforeAll
    public static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ParserService parser = new ParserService();
        DataRepository repository = new DataRepositoryImpl(jdbcTemplate);
        service = new DataService(parser, repository);
    }

    @Test
    public void parseTest() throws IOException {
        String request = "id;name;sex;\n" +
                "0;ричард;м;\n" +
                "1;жорж;м;\n" +
                "2;мария;ж;\n" +
                "3;пьер;м;";
        DataListDto response = service.parse(request);

        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));
        DataListDto expected = new DataListDto(list);

        Assertions.assertEquals(response, expected);
    }

    @Test
    public void getTest() {
        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));

        list.forEach(data -> {
            try {
                Data response = service.get(data.getName());
                Assertions.assertEquals(response, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
