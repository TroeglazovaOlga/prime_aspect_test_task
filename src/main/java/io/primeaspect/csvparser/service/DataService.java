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
    private final ParserService parser;
    private final DataRepository repository;

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
        repository.saveAll(resultList);
        return new DataListDto(resultList);
    }

    public List<Data> findAllByName(String name) throws IOException {
        return repository.findAllByName(name);
    }

    public DataListDto findAll() throws IOException {
        return new DataListDto(repository.findAll());
    }
}