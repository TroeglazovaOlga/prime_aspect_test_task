package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.FileDto;
import io.primeaspect.csvparser.dto.FileListDto;
import io.primeaspect.csvparser.jdbc.dao.FileDao;
import io.primeaspect.csvparser.model.File;
import io.primeaspect.csvparser.service.Parser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private Parser parser;
    private FileDao dao;

    public Controller(Parser parser, FileDao dao) {
        this.parser = parser;
        this.dao = dao;
    }

    @PostMapping("/parse")
    public FileListDto parse(@RequestBody String body) throws IOException {
        List<FileDto> resultList = new ArrayList<>();
        parser.parse(body).forEach((name, content) -> {
            resultList.add(new FileDto(name, content));
            dao.save(new File(name, content));
        });
        dao.findAll().forEach(System.out::println);
        return new FileListDto(resultList);
    }
}