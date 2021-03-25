package io.primeaspect.csvparser.test.service;

import io.primeaspect.csvparser.dto.request.DataRequest;
import io.primeaspect.csvparser.dto.response.DataListResponse;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.service.DataService;
import io.primeaspect.csvparser.service.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataServiceTest {
    private final ParserService parser = Mockito.mock(ParserService.class);
    private final DataRepository repository = Mockito.mock(DataRepository.class);
    private final DataService service = new DataService(parser, repository);

    @Test
    public void createTest() throws IOException {
        User user = new User("user");
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
                .map(set -> new Data(set.getKey(), set.getValue(), user))
                .collect(Collectors.toList());

        when(parser.parse(request)).thenReturn(expectedMap);

        DataListResponse response = service.create(new DataRequest(user, request));
        Assertions.assertEquals(response, new DataListResponse(expectedList));
    }

    @Test
    public void findAllByNameTest() throws IOException {
        Data requestData = new Data("path", "/hello/уточка;/hello/лошадка;/hello/собачка;");
        List<Data> requestList = new ArrayList<>();
        requestList.add(requestData);
        repository.saveAll(requestList);

        when(repository.findAllByName(requestData.getName())).thenReturn(requestList);

        DataListResponse response = service.findAllByName(requestData.getName());
        Assertions.assertEquals(response, new DataListResponse(requestList));
    }

    @Test
    public void findAllByUserNameTest() throws IOException {
        User user1 = new User("user1");
        User user2 = new User("user2");

        List<Data> requestList = new ArrayList<>();
        requestList.add(new Data("id", "0;1;2;3;", user1));
        requestList.add(new Data("name", "ричард;жорж;мария;пьер;", user2));

        List<Data> expectedList = new ArrayList<>();
        expectedList.add(new Data("id", "0;1;2;3;", user1));

        repository.saveAll(requestList);

        when(repository.findAllByUserName(user1.getName())).thenReturn(expectedList);
        DataListResponse responseList = service.findAllByUserName(user1.getName());
        Assertions.assertEquals(responseList, new DataListResponse(expectedList));
    }

    @Test
    public void findAllTest() throws IOException {
        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));

        when(repository.findAll()).thenReturn(list);

        DataListResponse responseList = service.findAll();
        Assertions.assertEquals(responseList, new DataListResponse(list));
    }

    @Test
    public void deleteAllByNameTest() {
        service.deleteAllByName("id");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).deleteAllByName(argumentCaptor.capture());
    }

    @Test
    public void deleteAllByUserNameTest() {
        service.deleteAllByUserName("user");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).deleteAllByUserName(argumentCaptor.capture());
    }

    @Test
    public void deleteAll() {
        service.deleteAll();
        verify(repository).deleteAll();
    }
}
