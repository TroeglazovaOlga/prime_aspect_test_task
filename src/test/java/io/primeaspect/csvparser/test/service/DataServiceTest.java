package io.primeaspect.csvparser.test.service;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.service.DataService;
import io.primeaspect.csvparser.service.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

public class DataServiceTest {
    private ParserService parser = Mockito.mock(ParserService.class);
    private DataRepository repository = Mockito.mock(DataRepository.class);
    private DataService service = new DataService(parser, repository);

    @Test
    public void parseTest() throws IOException {
        String request = "id;name;sex;\n" +
                "0;ричард;м;\n" +
                "1;жорж;м;\n" +
                "2;мария;ж;\n" +
                "3;пьер;м;";

        Map<String, String> expectedMap = new LinkedHashMap<>();
        expectedMap.put("id", "0;1;2;3;");
        expectedMap.put("name", "ричард;жорж;мария;пьер;");
        expectedMap.put("sex", "м;ж;");

        List<Data> expectedList = expectedMap.entrySet()
                .stream()
                .map(set -> new Data(set.getKey(), set.getValue()))
                .collect(Collectors.toList());

        when(parser.parse(request)).thenReturn(expectedMap);

        DataListDto response = service.parse(request);
        Assertions.assertEquals(response, new DataListDto(expectedList));
    }

    @Test
    public void getTest() throws IOException {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);
        repository.save(requestList);

        when(repository.getByName(requestData.getName())).thenReturn(requestList);

        List<Data> response = service.getByName(requestData.getName());
        Assertions.assertEquals(response, requestList);
    }
}
