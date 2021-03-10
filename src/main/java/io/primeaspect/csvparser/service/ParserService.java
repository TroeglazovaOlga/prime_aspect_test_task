package io.primeaspect.csvparser.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ParserService {
    public Map<String, String> parse(String content) throws IOException {
        Map<String, Set<String>> map = new HashMap<>();

        String[] rows = content.split("\n");
        String[] fileNames = rows[0].split(";");

        for (int i = 1; i < rows.length; i++) {
            String[] values = rows[i].split(";");

            for (int j = 0; j < values.length; j++) {
                if (map.containsKey(fileNames[j])) {
                    map.get(fileNames[j]).add(values[j]);
                } else {
                    Set<String> valuesSet = new HashSet<>();
                    valuesSet.add(values[j]);
                    map.put(fileNames[j], valuesSet);
                }
            }
        }

        Map<String, String> result = new HashMap<>();
        map.forEach((name, body) -> result.put(name, String.join(";", body)));

        return result;
    }
}