package io.primeaspect.csvparser.test;

import io.primeaspect.csvparser.controller.Controller;
import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.jdbc.repository.DataRepositoryImpl;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.service.DataService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataService service;
    private static DataRepositoryImpl repository;

    @BeforeAll
    public static void beforeAll() {
        List<Data> testContent = new LinkedList<>();
        testContent.add(new Data("id", "0;1;2;3;"));
        repository.save(testContent);
    }

    @Test
    public void parseTest() throws Exception {
        String content = "id;name;sex;\n" +
                "0;ричард;м;\n" +
                "1;жорж;м;\n" +
                "2;мария;ж;\n" +
                "3;пьер;м;";
        mvc.perform(post("/parse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());

        List<Data> list = new ArrayList<>();
        list.add(new Data("id", "0;1;2;3;"));
        list.add(new Data("name", "ричард;жорж;мария;пьер;"));
        list.add(new Data("sex", "м;ж;"));
        DataListDto expected = new DataListDto(list);

        Mockito.when(service.parse(content)).thenReturn(expected);
    }

    @Test
    public void getTest() throws Exception {
        mvc.perform(get("/data/{name}", "id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.when(service.get("id")).thenReturn(new Data("id", "0;1;2;3;"));
    }
}