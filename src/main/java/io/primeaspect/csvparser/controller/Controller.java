package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {
    private DataService service;

    public Controller(DataService service) {
        this.service = service;
    }

    @PostMapping("/parse")
    public DataListDto parse(@RequestBody String body) throws IOException {
        return service.parse(body);
    }

    @GetMapping("/getByName")
    public Data get(@RequestBody String body) throws IOException {
        return service.get(body);
    }
}