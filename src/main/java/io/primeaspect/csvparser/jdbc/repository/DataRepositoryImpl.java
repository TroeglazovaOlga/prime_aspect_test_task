package io.primeaspect.csvparser.jdbc.repository;

import io.primeaspect.csvparser.model.Data;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DataRepositoryImpl implements DataRepository {
    private JdbcTemplate jdbcTemplate;

    public DataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] save(List<Data> data) {
        String updateSql = "insert into data (name, content) values(?,?)";
        return jdbcTemplate.batchUpdate(
                updateSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ps.setString(1, data.get(i).getName());
                        ps.setString(2, data.get(i).getContent());
                    }

                    public int getBatchSize() {
                        return data.size();
                    }
                });
    }

    public Data get(String name) {
        String selectSql = "select * from data where name = ?";
        return jdbcTemplate.queryForObject(
                selectSql,
                new Object[]{name},
                (result, rowNum) ->
                        new Data(
                                result.getString("name"),
                                result.getString("content")
                        )
        );
    }

    public List<Data> getAll() {
        String selectSql = "select * from data";
        return jdbcTemplate.query(
                selectSql,
                (result, rowNum) ->
                        new Data(
                                result.getString("name"),
                                result.getString("content")
                        )
        );
    }

}
