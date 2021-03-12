package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.service.DataService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @GetMapping("/data/{name}")
    public Data get(@PathVariable @NotNull String name) throws IOException {
        return service.get(name);
    }
}