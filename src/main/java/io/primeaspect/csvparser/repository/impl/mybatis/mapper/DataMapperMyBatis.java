package io.primeaspect.csvparser.repository.impl.mybatis.mapper;

import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataMapperMyBatis {
    @Insert({
            "<script>",
                "INSERT INTO data(name, content, user_id) ",
                "VALUES ",
                "<foreach item='item' collection='dataList' separator=',' >",
                    "( #{item.name}, #{item.content}, #{user.id} )",
                "</foreach>",
            "</script>"
    })
    void createData(List<Data> dataList, @Param("user") User user);

    @Select("SELECT id, name, content FROM data WHERE name = #{name}")
    List<Data> findAllByName(@Param("name") String name);

    @Select("SELECT data.id, data.name, data.content, data.user_id FROM data LEFT OUTER JOIN user ON data.user_id = user.id WHERE user.name #{name}")
    @Result(property = "user", column = "name", one = @One(select = "findUserByName"))
    List<Data> findAllByUserName(@Param("name") String name);

    @Select("SELECT id, name, content FROM data")
    List<Data> findAll();

    @Delete("DELETE FROM data WHERE name = #{name}")
    void deleteByName(String name);

    @Delete("DELETE FROM data WHERE user_id = (SELECT id FROM users WHERE name = #{name})")
    void deleteByUserName(String name);

    @Delete("DELETE FROM data")
    void deleteAll();

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(String name);

    @Insert("INSERT INTO user (name) VALUES (#{user.name})")
    void createUser(@Param("user") User user);
}