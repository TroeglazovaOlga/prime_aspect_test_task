package io.primeaspect.csvparser.test.repository.mybatis;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.repository.impl.mybatis.repository.DataRepositoryMyBatis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataRepositoryMyBatisUnitTest {
    private final DataMapper mapper = Mockito.mock(DataMapper.class);
    private final DataRepositoryMyBatis repository = new DataRepositoryMyBatis(mapper);

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));

        repository.saveAll(requestList);
        verify(mapper).createData(requestList);
    }

    @Test
    public void findAllByNameTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);
        String request = "id";

        when(mapper.findAllByName(eq(request))).thenReturn(requestList);
        List<Data> responseList = repository.findAllByName(request);

        verify(mapper).findAllByName(request);
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);

        when(mapper.findAll()).thenReturn(requestList);
        List<Data> responseList = repository.findAll();

        verify(mapper).findAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void deleteAll() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);

        repository.deleteAll();
        verify(mapper).deleteAll();
    }
}