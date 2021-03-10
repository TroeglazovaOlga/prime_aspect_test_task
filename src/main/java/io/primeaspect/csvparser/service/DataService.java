package io.primeaspect.csvparser.service;

import io.primeaspect.csvparser.dto.DataDto;
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
        List<DataDto> resultList = new ArrayList<>();
        parser.parse(request).forEach((name, content) -> {
            resultList.add(new DataDto(name, content));
            repository.save(new Data(name, content));
        });
        repository.findAll().forEach(System.out::println);
        return new DataListDto(resultList);
    }
}