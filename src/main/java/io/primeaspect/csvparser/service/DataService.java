package io.primeaspect.csvparser.service;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.repository.DataRepository;
import io.primeaspect.csvparser.model.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {
    private ParserService parser;
    private DataRepository repository;

    public DataService(ParserService parser, DataRepository repository) {
        this.parser = parser;
        this.repository = repository;
    }

    public DataListDto parse(String request) throws IOException {
        List<Data> resultList = parser.parse(request)
                .entrySet()
                .stream()
                .map(set -> new Data(set.getKey(), set.getValue()))
                .collect(Collectors.toList());
        repository.save(resultList);
        return new DataListDto(resultList);
    }

    public Data get(String name) throws IOException {
        return repository.get(name);
    }

    public DataListDto getAll() throws IOException {
        return new DataListDto(repository.getAll());
    }
}