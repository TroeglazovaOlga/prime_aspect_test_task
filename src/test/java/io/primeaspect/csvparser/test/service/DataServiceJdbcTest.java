package io.primeaspect.csvparser.test.service;

import io.primeaspect.csvparser.repository.impl.jdbc.DataRepositoryJdbc;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.service.DataService;
import io.primeaspect.csvparser.service.ParserService;
import io.primeaspect.csvparser.test.base.DataServiceBaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class DataServiceJdbcTest {
    private static DataServiceBaseTest base;

    @BeforeAll
    public static void beforeAll() {
        ParserService parser = Mockito.mock(ParserService.class);
        DataRepository repository = Mockito.mock(DataRepositoryJdbc.class);
        DataService service = new DataService(parser, repository);

        base = new DataServiceBaseTest(parser, repository, service);
    }

    @Test
    public void parseTest() throws IOException {
        base.parseTest();
    }

    @Test
    public void getTest() throws IOException {
        base.getTest();
    }
}
