package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.request.DataByUserRequest;
import io.primeaspect.csvparser.dto.response.DataListResponse;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.dto.request.DataRequest;
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

    @PostMapping("/create")
    public DataListResponse create(@RequestBody DataRequest body) throws IOException {
        return service.create(body);
    }

    @PostMapping("/create/user")
    public DataListResponse createByUser(@RequestBody DataByUserRequest body) throws IOException {
        return service.createByUser(body);
    }

    @GetMapping("/data/{name}")
    public List<Data> findAllByName(@PathVariable @NotNull String name) throws IOException {
        return service.findAllByName(name);
    }

    @GetMapping("/data/user/{name}")
    public List<Data> findAllByUserName(@PathVariable @NotNull String name) {
        return service.findAllByUserName(name);
    }

    @GetMapping("/data")
    public DataListResponse findAll() throws IOException {
        return service.findAll();
    }
}