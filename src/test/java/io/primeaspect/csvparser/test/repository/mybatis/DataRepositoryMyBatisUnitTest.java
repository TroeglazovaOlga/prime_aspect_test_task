package io.primeaspect.csvparser.test.repository.mybatis;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapperMyBatis;
import io.primeaspect.csvparser.repository.impl.mybatis.repository.DataRepositoryMyBatis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DataRepositoryMyBatisUnitTest {
    private final DataMapperMyBatis mapper = Mockito.mock(DataMapperMyBatis.class);
    private final DataRepositoryMyBatis repository = new DataRepositoryMyBatis(mapper);

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));

        repository.saveAll(requestList);
        verify(mapper).createData(requestList, user);
    }

    @Test
    public void findAllByNameTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        String request = "id";

        when(mapper.findAllByName(eq(request))).thenReturn(requestList);
        List<Data> responseList = repository.findAllByName(request);

        verify(mapper).findAllByName(request);
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllByUserNameTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        String request = "user";

        when(mapper.findAllByUserName(eq(request))).thenReturn(requestList);
        List<Data> responseList = repository.findAllByUserName(request);

        verify(mapper).findAllByUserName(request);
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllTest() {
        User user = new User("user");
        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));

        when(mapper.findAll()).thenReturn(requestList);
        List<Data> responseList = repository.findAll();

        verify(mapper).findAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void deleteAllByName() {
        String request = "id";
        repository.deleteAllByName(request);
        verify(mapper).deleteByName(eq(request));
    }

    @Test
    public void deleteAllByUserName() {
        String request = "user";
        repository.deleteAllByUserName(request);
        verify(mapper).deleteByUserName(eq(request));
    }

    @Test
    public void deleteAll() {
        repository.deleteAll();
        verify(mapper).deleteAll();
    }
}