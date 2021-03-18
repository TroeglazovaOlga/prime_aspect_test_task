package io.primeaspect.csvparser.repository.impl.mybatis.mapper;

import io.primeaspect.csvparser.model.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataMapper {
    @Insert({
        "<script>",
            "insert into data(name, content) ",
            "values ",
            "<foreach item='item' collection='dataList' separator=',' >",
                "( #{item.name}, #{item.content} )",
            "</foreach>",
        "</script>"
    })
    void createData(List<Data> dataList);

    @Select("SELECT id, name, content FROM data WHERE name = #{name}")
    Data get(@Param("name") String name);

    @Select("SELECT id, name, content FROM data")
    List<Data> getAll();

    @Delete("delete from data")
    void deleteAll();
}
