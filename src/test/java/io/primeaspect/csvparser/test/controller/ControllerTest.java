package io.primeaspect.csvparser.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.primeaspect.csvparser.controller.Controller;
import io.primeaspect.csvparser.dto.request.DataByUserRequest;
import io.primeaspect.csvparser.dto.response.DataListResponse;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.dto.request.DataRequest;
import io.primeaspect.csvparser.model.User;
import io.primeaspect.csvparser.service.DataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private DataService service;

    @Test
    public void createTest() throws Exception {
        DataRequest request = new DataRequest("id;name;sex;\n" +
                "0;ричард;м;\n" +
                "1;жорж;м;\n" +
                "2;мария;ж;\n" +
                "3;пьер;м;");

        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));
        DataListResponse expected = new DataListResponse(list);

        when(service.create(request)).thenReturn(expected);
        mvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.list", hasSize(3)))
                .andExpect(jsonPath("$.list[0].name", is("id")))
                .andExpect(jsonPath("$.list[0].content", is("0;1;2;3;")))
                .andExpect(jsonPath("$.list[1].name", is("name")))
                .andExpect(jsonPath("$.list[1].content", is("ричард;жорж;мария;пьер;")))
                .andExpect(jsonPath("$.list[2].name", is("sex")))
                .andExpect(jsonPath("$.list[2].content", is("м;ж;")));

        ArgumentCaptor<DataRequest> argumentCaptor = ArgumentCaptor.forClass(DataRequest.class);
        verify(service).create(argumentCaptor.capture());
        Assertions.assertEquals(request, argumentCaptor.getValue());
    }

    @Test
    public void createByUserTest() throws Exception {
        User user = new User("user");
        DataByUserRequest request = new DataByUserRequest(user, "id;name;sex;\n" +
                "0;ричард;м;\n" +
                "1;жорж;м;\n" +
                "2;мария;ж;\n" +
                "3;пьер;м;");

        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));
        DataListResponse expected = new DataListResponse(list);

        when(service.createByUser(request)).thenReturn(expected);
        mvc.perform(post("/create/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.list", hasSize(3)))
                .andExpect(jsonPath("$.list[0].name", is("id")))
                .andExpect(jsonPath("$.list[0].content", is("0;1;2;3;")))
                .andExpect(jsonPath("$.list[1].name", is("name")))
                .andExpect(jsonPath("$.list[1].content", is("ричард;жорж;мария;пьер;")))
                .andExpect(jsonPath("$.list[2].name", is("sex")))
                .andExpect(jsonPath("$.list[2].content", is("м;ж;")));

        ArgumentCaptor<DataByUserRequest> argumentCaptor = ArgumentCaptor.forClass(DataByUserRequest.class);
        verify(service).createByUser(argumentCaptor.capture());
        Assertions.assertEquals(request, argumentCaptor.getValue());
    }

    @Test
    public void findAllByNameTest() throws Exception {
        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        when(service.findAllByName("id")).thenReturn(list);
        mvc.perform(get("/data/{name}", "id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("id")))
                .andExpect(jsonPath("$[0].content", is("0;1;2;3;")));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(service).findAllByName(argumentCaptor.capture());
        Assertions.assertEquals("id", argumentCaptor.getValue());
    }

    @Test
    public void findAllByUserNameTest() throws Exception {
        User user1 = new User("user1");
        User user2 = new User("user2");

        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;", user1));
        list.add(new Data("name", "ричард;жорж;мария;пьер;", user2));

        List<Data> expectedList = new ArrayList<>();
        expectedList.add(new Data("id", "0;1;2;3;", user1));

        when(service.findAllByUserName("user1")).thenReturn(expectedList);
        mvc.perform(get("/data/user/{name}", "user1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("id")))
                .andExpect(jsonPath("$[0].content", is("0;1;2;3;")));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(service).findAllByUserName(argumentCaptor.capture());
        Assertions.assertEquals("user1", argumentCaptor.getValue());
    }
}