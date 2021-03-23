package io.primeaspect.csvparser.test.repository.hibernate;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.hibernate.CustomCrudRepository;
import io.primeaspect.csvparser.repository.impl.hibernate.DataRepositoryHibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

public class DataRepositoryHibernateUnitTest {
    private final CustomCrudRepository crudRepository = Mockito.mock(CustomCrudRepository.class);
    private final DataRepository repository = new DataRepositoryHibernate(crudRepository);

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));

        repository.saveAll(requestList);
        verify(crudRepository).saveAll(requestList);
    }

    @Test
    public void findAllByNameTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);
        String request = "id";

        when(crudRepository.findAllByName(eq(request))).thenReturn(requestList);
        List<Data> responseList = repository.findAllByName(request);

        verify(crudRepository).findAllByName(request);
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);

        when(crudRepository.findAll()).thenReturn(requestList);
        List<Data> responseList = repository.findAll();

        verify(crudRepository).findAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void deleteAll() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        repository.saveAll(requestList);

        repository.deleteAll();
        verify(crudRepository).deleteAll();
    }
}
