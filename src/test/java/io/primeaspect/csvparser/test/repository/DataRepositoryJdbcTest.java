package io.primeaspect.csvparser.test.repository;

import io.primeaspect.csvparser.jdbc.repository.DataRepositoryJdbc;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.test.base.DataRepositoryBaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class DataRepositoryJdbcTest {
    private static DataRepositoryBaseTest base;

    @BeforeAll
    public static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DataRepository repository = new DataRepositoryJdbc(jdbcTemplate);

        base = new DataRepositoryBaseTest(repository);
    }

    @Test
    public void saveTest() {
        base.saveTest();
    }

    @Test
    public void getTest() {
        base.getTest();
    }
}