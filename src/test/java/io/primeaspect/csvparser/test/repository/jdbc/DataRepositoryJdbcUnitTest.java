package io.primeaspect.csvparser.test.repository.jdbc;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.jdbc.DataRepositoryJdbc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.mockito.Mockito.*;

public class DataRepositoryJdbcUnitTest {
    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final DataRepository repository = new DataRepositoryJdbc(jdbcTemplate);

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));

        repository.saveAll(requestList);
        verify(jdbcTemplate).batchUpdate(anyString(),
                any(BatchPreparedStatementSetter.class));
    }

    @Test
    public void findAllByNameTest() {

        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);
        String request = "id";

        when(jdbcTemplate.query(anyString(), any(Object[].class),
                any(RowMapper.class))).thenReturn(requestList);

        List<Data> responseList = repository.findAllByName(request);

        verify(jdbcTemplate).query(anyString(), any(Object[].class),
                any(RowMapper.class));
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);

        when(jdbcTemplate.query(anyString(),
                any(RowMapper.class))).thenReturn(requestList);
        List<Data> responseList = repository.findAll();

        verify(jdbcTemplate).query(anyString(),
                any(RowMapper.class));
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void deleteAll() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);

        repository.deleteAll();
        verify(jdbcTemplate).update(anyString());
    }
}