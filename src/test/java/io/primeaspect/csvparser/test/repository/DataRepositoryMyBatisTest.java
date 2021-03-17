package io.primeaspect.csvparser.test.repository;

import io.primeaspect.csvparser.mybatis.mapper.DataMapper;
import io.primeaspect.csvparser.mybatis.repository.DataRepositoryMyBatis;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.test.base.DataRepositoryBaseTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.Reader;

public class DataRepositoryMyBatisTest {
    private static DataRepositoryBaseTest base;

    @BeforeAll
    public static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            DataRepository repository = new DataRepositoryMyBatis(sqlSessionFactory.openSession().getMapper(DataMapper.class));
            base = new DataRepositoryBaseTest(repository);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveTest() {
        base.saveTest();
    }

    @Test
    public void getTest() {
        base.getTest();
    }
}