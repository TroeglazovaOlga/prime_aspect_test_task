package io.primeaspect.csvparser.service;

import io.primeaspect.csvparser.dto.DataListDto;
import io.primeaspect.csvparser.jdbc.repository.DataRepository;
import io.primeaspect.csvparser.model.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private ParserService parser;
    private DataRepository repository;

    public DataService(ParserService parser, DataRepository repository) {
        this.parser = parser;
        this.repository = repository;
    }

    public DataListDto parse(String request) throws IOException {
        List<Data> resultList = new ArrayList<>();
        parser.parse(request).forEach((name, content) -> {
            resultList.add(new Data(name, content));
        });
        repository.save(resultList);
        return new DataListDto(resultList);
    }

    public Data get(String name) throws IOException {
        return repository.findByName(name);
    }
}