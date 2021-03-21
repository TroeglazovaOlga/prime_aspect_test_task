package io.primeaspect.csvparser.test.repository;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.repository.impl.hibernate.CustomCrudRepository;
import io.primeaspect.csvparser.repository.impl.hibernate.DataRepositoryHibernate;
import io.primeaspect.csvparser.test.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {TestConfiguration.class, DataRepositoryHibernate.class})
@ComponentScan(basePackages = "io.primeaspect.csvparser")
@EnableJpaRepositories(basePackageClasses = CustomCrudRepository.class)
public class DataRepositoryHibernateTest {
    @Autowired
    @Qualifier("dataRepositoryHibernate")
    private DataRepository repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveTest() {
        List<Data> requestList = new java.util.ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;"));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new Data("sex", "м;ж;"));

        repository.save(requestList);
        List<Data> responseList = repository.getAll();
        Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void getTest() {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);

        repository.save(requestList);
        List<Data> response = repository.getByName(requestData.getName());
        Assertions.assertEquals(requestList, response);
    }
}