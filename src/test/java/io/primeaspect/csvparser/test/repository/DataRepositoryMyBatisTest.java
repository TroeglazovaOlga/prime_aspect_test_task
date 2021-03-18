package io.primeaspect.csvparser.test.repository;

import io.primeaspect.csvparser.repository.impl.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.repository.impl.mybatis.repository.DataRepositoryMyBatis;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.test.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = {TestConfiguration.class, DataRepositoryMyBatis.class, DataMapper.class})
@ComponentScan(basePackages="io.primeaspect.csvparser")
@MapperScan("io.primeaspect.csvparser.repository.impl.mybatis.mapper")
public class DataRepositoryMyBatisTest {
    @Autowired
    private DataRepository repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void saveTest() {
        java.util.List<io.primeaspect.csvparser.model.Data> requestList = new java.util.ArrayList<>();
        requestList.add(new io.primeaspect.csvparser.model.Data("id", "0;1;2;3;"));
        requestList.add(new io.primeaspect.csvparser.model.Data("name", "ричард;жорж;мария;пьер;"));
        requestList.add(new io.primeaspect.csvparser.model.Data("sex", "м;ж;"));

        repository.save(requestList);
        java.util.List<io.primeaspect.csvparser.model.Data> responseList = repository.getAll();
        org.junit.jupiter.api.Assertions.assertEquals(requestList, responseList);
    }

    @Test
    public void getTest() {
        io.primeaspect.csvparser.model.Data requestData = new io.primeaspect.csvparser.model.Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        java.util.List<io.primeaspect.csvparser.model.Data> requestList = new java.util.ArrayList<>();
        requestList.add(requestData);

        repository.save(requestList);
        io.primeaspect.csvparser.model.Data response = repository.get(requestData.getName());
        org.junit.jupiter.api.Assertions.assertEquals(requestData, response);
    }
}
