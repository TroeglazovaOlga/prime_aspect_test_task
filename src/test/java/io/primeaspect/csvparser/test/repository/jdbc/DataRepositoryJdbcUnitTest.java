package io.primeaspect.csvparser.test.repository.jdbc;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.jdbc.mapper.DataMapperJdbc;
import io.primeaspect.csvparser.repository.impl.jdbc.repository.DataRepositoryJdbc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DataRepositoryJdbcUnitTest {
    private final DataMapperJdbc mapper = Mockito.mock(DataMapperJdbc.class);
    private final DataRepository repository = new DataRepositoryJdbc(mapper);

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));

        List<User> users = new ArrayList<>();
        users.add(user);

        when(mapper.findUserByName(anyString())).thenReturn(users);
        repository.saveAll(requestList);
        verify(mapper).saveDataList(anyList());
    }

    @Test
    public void findAllByNameTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        String request = "id";

        when(mapper.findAllByName(anyString())).thenReturn(requestList);
        List<Data> responseList = repository.findAllByName(request);

        verify(mapper).findAllByName(eq(request));
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllByUserNameTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        String request = "user";

        when(mapper.findAllByUserName(anyString())).thenReturn(requestList);
        List<Data> responseList = repository.findAllByUserName(request);

        verify(mapper).findAllByUserName(eq(request));
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
        repository.deleteAllByName("id");
        verify(mapper).deleteAllByName(anyString());
    }

    @Test
    public void deleteAll() {
        repository.deleteAll();
        verify(mapper).deleteData();
        verify(mapper).deleteUsers();
    }
}