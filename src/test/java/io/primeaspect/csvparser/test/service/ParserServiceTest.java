package io.primeaspect.csvparser.test.service;

import io.primeaspect.csvparser.service.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParserServiceTest {
    private static ParserService parser = new ParserService();

    @Test
    public void parseTest() throws IOException {
        String request = "id;name;sex;\n" +
                "0;ричард;м;\n" +
                "1;жорж;м;\n" +
                "2;мария;ж;\n" +
                "3;пьер;м;";
        Map<String, String> response = parser.parse(request);

        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("id", "0;1;2;3;");
        expected.put("name", "ричард;жорж;мария;пьер;");
        expected.put("sex", "м;ж;");

        Assertions.assertEquals(response, expected);
    }
}
