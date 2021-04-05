package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.request.DataRequest;
import io.primeaspect.csvparser.dto.response.DataListResponse;
import io.primeaspect.csvparser.service.DataService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
public class Controller {
    private final DataService service;

    public Controller(DataService service) {
        this.service = service;
    }

    @PostMapping("/data")
    public DataListResponse create(@RequestBody DataRequest body) throws IOException {
        return service.create(body);
    }

    @GetMapping("/data/{name}")
    public DataListResponse findAllByName(@PathVariable @NotNull String name) throws IOException {
        return service.findAllByName(name);
    }

    @GetMapping("/data/user/{name}")
    public DataListResponse findAllByUserName(@PathVariable @NotNull String name) {
        return service.findAllByUserName(name);
    }

    @GetMapping("/data")
    public DataListResponse findAll() throws IOException {
        return service.findAll();
    }

    @DeleteMapping("/data/{name}")
    public void deleteAllByName(@PathVariable @NotNull String name) {
        this.service.deleteAllByName(name);
    }

    @DeleteMapping("/data/user/{name}")
    public void deleteAllByUserName(@PathVariable @NotNull String name) {
        this.service.deleteAllByUserName(name);
    }

    @DeleteMapping("/data")
    public void deleteAll() {
        this.service.deleteAll();
    }
}