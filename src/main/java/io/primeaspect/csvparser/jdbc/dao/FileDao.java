package io.primeaspect.csvparser.jdbc.dao;

import io.primeaspect.csvparser.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FileDao() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:./h2db");
        dataSource.setPassword("");

        this.jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("drop table files if exists");
        jdbcTemplate.execute("create table files(" +
                "id serial, name varchar(255), content varchar(255))");
    }

    public int save(File file) {
        return jdbcTemplate.update(
                "insert into files (name, content) values(?,?)",
                file.getName(), file.getContent());
    }

    public List<File> findAll() {
        return jdbcTemplate.query(
                "select * from files",
                (result, rowNum) ->
                        new File(
                                result.getString("name"),
                                result.getString("content")
                        )
        );
    }
}
