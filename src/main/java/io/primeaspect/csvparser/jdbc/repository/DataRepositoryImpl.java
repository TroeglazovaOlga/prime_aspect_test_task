package io.primeaspect.csvparser.jdbc.repository;

import io.primeaspect.csvparser.model.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepositoryImpl implements DataRepository {
    private JdbcTemplate jdbcTemplate;

    public DataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Data data) {
        return jdbcTemplate.update(
                "insert into data (name, content) values(?,?)",
                data.getName(), data.getContent());
    }

    public List<Data> findAll() {
        return jdbcTemplate.query(
                "select * from data",
                (result, rowNum) ->
                        new Data(
                                result.getString("name"),
                                result.getString("content")
                        )
        );
    }
}
