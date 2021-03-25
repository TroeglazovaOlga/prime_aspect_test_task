package io.primeaspect.csvparser.test.repository.hibernate;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.repository.impl.hibernate.DataCrudRepository;
import io.primeaspect.csvparser.repository.impl.hibernate.DataRepositoryHibernate;
import io.primeaspect.csvparser.test.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {TestConfiguration.class, DataRepositoryHibernate.class})
@ComponentScan(basePackages = "io.primeaspect.csvparser")
@EnableJpaRepositories(basePackageClasses = DataCrudRepository.class)
public class DataRepositoryHibernateTest {
    @Autowired
    private DataRepositoryHibernate repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveAllTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;", user));
        requestList.add(new Data("sex", "м;ж;", user));

        repository.saveAll(requestList);
        List<Data> responseList = repository.findAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void findAllByNameTest() {
        User user = new User("user");
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;", user);
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);

        repository.saveAll(requestList);
        List<Data> response = repository.findAllByName(requestData.getName());
        Assertions.assertEquals(requestList, response);
    }

    @Test
    public void findAllByUserNameTest() {
        User user1 = new User("user1");
        User user2 = new User("user2");

        List<Data> requestByUser1 = new ArrayList<>();
        requestByUser1.add(new Data("id", "0;1;2;3;", user1));
        List<Data> requestByUser2 = new ArrayList<>();
        requestByUser2.add(new Data("name", "ричард;жорж;мария;пьер;", user2));

        List<Data> expectedList = new ArrayList<>();
        expectedList.add(new Data("id", "0;1;2;3;", user1));

        repository.saveAll(requestByUser1);
        repository.saveAll(requestByUser2);

        List<Data> responseList = repository.findAllByUserName(user1.getName());
        Assertions.assertEquals(expectedList, responseList);
    }

    @Test
    public void findAllTest() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;", user));
        requestList.add(new Data("sex", "м;ж;", user));
        repository.saveAll(requestList);

        List<Data> response = repository.findAll();
        Assertions.assertEquals(requestList, response);
    }

    @Test
    public void deleteAllByName() {
        User user1 = new User("user1");
        User user2 = new User("user2");

        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user1));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;", user2));
        repository.saveAll(requestList);

        String request = "id";

        repository.deleteAllByName(request);
        List<Data> resultList = repository.findAllByName(request);
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    public void deleteAllByUserName() {
        User user1 = new User("user1");
        User user2 = new User("user2");

        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user1));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;", user2));
        repository.saveAll(requestList);

        String request = "user1";

        repository.deleteAllByUserName(request);
        List<Data> resultList = repository.findAllByUserName(request);
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    public void deleteAll() {
        User user = new User("user");
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;", user));
        requestList.add(new Data("sex", "м;ж;", user));
        repository.saveAll(requestList);

        repository.deleteAll();
        List<Data> resultList = repository.findAll();
        Assertions.assertTrue(resultList.isEmpty());
    }
}