package io.primeaspect.csvparser.repository.impl.jdbc.mapper;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DataMapperJdbc {
    private final JdbcTemplate jdbcTemplate;

    public DataMapperJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveDataList(List<Data> dataList) {
        String updateSql = "insert into data (name, content, user_id) values(?,?,?)";
        jdbcTemplate.batchUpdate(
                updateSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ps.setString(1, dataList.get(i).getName());
                        ps.setString(2, dataList.get(i).getContent());
                        ps.setLong(3, dataList.get(i).getUser().getId());
                    }

                    public int getBatchSize() {
                        return dataList.size();
                    }
                });
    }

    public List<Data> findAllByName(String name) {
        String selectSql = "select * from data where name = ?";
        return jdbcTemplate.query(
                selectSql,
                new Object[]{name},
                (rs, rowNum) ->
                        new Data(
                                rs.getString("name"),
                                rs.getString("content"),
                                findUserById(rs.getLong("user_id")).get(0)
                        )
        );
    }

    public List<Data> findAllByUserName(String name) {
        String selectSql = "SELECT data.id, data.name, data.content, data.user_id " +
                " FROM data LEFT OUTER JOIN user ON data.user_id = user.id WHERE user.name = ?";
        return jdbcTemplate.query(
                selectSql,
                new Object[]{name},
                (rs, rowNum) ->
                        new Data(
                                rs.getString("name"),
                                rs.getString("content"),
                                findUserById(rs.getLong("user_id")).get(0)
                        )
        );
    }

    public List<Data> findAll() {
        String selectSql = "select * from data";
        return jdbcTemplate.query(
                selectSql,
                (result, rowNum) ->
                        new Data(
                                result.getString("name"),
                                result.getString("content"),
                                findUserById(result.getLong("user_id")).get(0)
                        )
        );
    }

    public void deleteAllByName(String name) {
        jdbcTemplate.update("delete from data where name = '" + name + "'");
    }

    public void deleteAllByUserId(long id) {
        jdbcTemplate.update("delete from data where user_id = '" + id + "'");
    }

    public void deleteData() {
        jdbcTemplate.update("delete from data");
    }

    public void deleteUsers() {
        jdbcTemplate.update("delete from user");
    }

    public void saveUser(User user) {
        jdbcTemplate.update("insert into user (name) values (?)", user.getName());
    }

    public List<User> findUserByName(String name) {
        String selectSql = "select * from user where name = '" + name + "'";
        return jdbcTemplate.query(
                selectSql,
                (result, rowNum) ->
                        new User(
                                result.getLong("id"),
                                result.getString("name")
                        )
        );
    }

    public List<User> findUserById(Long id) {
        String selectSql = "select * from user where id = '" + id + "'";
        return jdbcTemplate.query(
                selectSql,
                (result, rowNum) ->
                        new User(
                                result.getLong("id"),
                                result.getString("name")
                        )
        );
    }
}
