package io.primeaspect.csvparser.controller;

import io.primeaspect.csvparser.dto.EntryDto;
import io.primeaspect.csvparser.dto.EntryListDto;
import io.primeaspect.csvparser.parserservice.Parser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private Parser parser;

    public Controller(Parser parser) {
        this.parser = parser;
    }

    @PostMapping("/parse")
    public EntryListDto parse(@RequestBody String body) throws IOException {
        List<EntryDto> resultList = new ArrayList<>();
        parser.parse(body).forEach((name, content) -> {
            resultList.add(new EntryDto(name, content));
        });
        return new EntryListDto(resultList);
    }
}