package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.model.DataRequest;
import io.primeaspect.csvparser.service.DataService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    private final DataService service;

    public Controller(DataService service) {
        this.service = service;
    }

    @PostMapping("/parse")
    public DataListDto parse(@RequestBody DataRequest body) throws IOException {
        return service.parse(body.getContent());
    }

    @GetMapping("/data/{name}")
    public List<Data> findAllByName(@PathVariable @NotNull String name) throws IOException {
        return service.findAllByName(name);
    }

    @GetMapping("/data")
    public DataListDto findAll() throws IOException {
        return service.findAll();
    }
}
