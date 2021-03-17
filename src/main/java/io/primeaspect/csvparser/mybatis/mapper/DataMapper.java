package io.primeaspect.csvparser.mybatis.mapper;

import io.primeaspect.csvparser.model.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataMapper {
    @Insert({
            "<script>",
            "insert into data (name, content) ",
            "values ",
            "<foreach item='item' collection='list' separator=',' >",
            "( #{item.name}, #{item.content} )",
            "</foreach>",
            "</script>"
    })
    void save(List<Data> data);

    @Select("SELECT id, name, content FROM data WHERE name = #{name}")
    Data get(@Param("name") String name);

    @Select("SELECT id, name, content FROM data")
    List<Data> getAll();
}