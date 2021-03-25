package io.primeaspect.csvparser.repository.impl.mybatis.mapper;

import io.primeaspect.csvparser.model.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataMapper {
    @Insert({
            "<script>",
                "INSERT INTO data(name, content) ",
                "VALUES ",
                "<foreach item='item' collection='dataList' separator=',' >",
                    "( #{item.name}, #{item.content} )",
                "</foreach>",
            "</script>"
    })
    void createData(List<Data> dataList);

    @Select("SELECT id, name, content FROM data WHERE name = #{name}")
    List<Data> findAllByName(@Param("name") String name);

    @Select("SELECT id, name, content FROM data")
    List<Data> findAll();

    @Delete("DELETE FROM data WHERE name = #{name")
    void deleteByName(String name);

    @Delete("DELETE FROM data")
    void deleteAll();
}