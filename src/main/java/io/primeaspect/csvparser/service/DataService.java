package io.primeaspect.csvparser.service;

import io.primeaspect.csvparser.dto.request.DataRequest;
import io.primeaspect.csvparser.dto.response.DataListResponse;
import io.primeaspect.csvparser.model.Data;
import io.primeaspect.csvparser.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {
    private final ParserService parser;
    private final DataRepository repository;

    public DataService(ParserService parser, DataRepository repository) {
        this.parser = parser;
        this.repository = repository;
    }

    public DataListResponse create(DataRequest request) throws IOException {
        List<Data> resultList = parser.parse(request.getContent())
                .entrySet()
                .stream()
                .map(set -> new Data(set.getKey(), set.getValue()))
                .collect(Collectors.toList());
        resultList.forEach(result -> result.setUser(request.getUser()));

        repository.saveAll(resultList);
        return new DataListResponse(resultList);
    }

    public DataListResponse findAllByName(String name) throws IOException {
        return new DataListResponse(repository.findAllByName(name));
    }

    public DataListResponse findAllByUserName(String name) {
        return new DataListResponse(repository.findAllByUserName(name));
    }

    public DataListResponse findAll() throws IOException {
        return new DataListResponse(repository.findAll());
    }

    public void deleteAllByName(String name) {
        this.repository.deleteAllByName(name);
    }

    public void deleteAllByUserName(String name) {
        this.repository.deleteAllByUserName(name);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }
}