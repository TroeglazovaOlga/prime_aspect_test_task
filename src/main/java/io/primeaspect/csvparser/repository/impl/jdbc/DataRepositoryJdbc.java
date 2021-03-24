package io.primeaspect.csvparser.repository.impl.jdbc;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DataRepositoryJdbc implements DataRepository {
    private final JdbcTemplate jdbcTemplate;

    public DataRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(List<Data> data) {
        String updateSql = "insert into data (name, content) values(?,?)";
        jdbcTemplate.batchUpdate(
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

    @Override
    public void saveAllByUser(List<Data> data) {

    }

    @Override
    public List<Data> findAllByName(String name) {
        String selectSql = "select * from data where name = ?";
        return jdbcTemplate.query(
                selectSql,
                new Object[]{name},
                (rs, rowNum) ->
                        new Data(
                                rs.getString("name"),
                                rs.getString("content")
                        )
        );
    }

    @Override
    public List<Data> findAllByUserName(String name) {
        return null;
    }

    @Override
    public List<Data> findAll() {
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

    @Override
    public int count() {
        String selectSql = "select count (*) from data";
        return jdbcTemplate.queryForObject(selectSql, Integer.class);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from data");
    }

}
