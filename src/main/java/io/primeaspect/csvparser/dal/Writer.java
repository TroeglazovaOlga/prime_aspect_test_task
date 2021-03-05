package io.primeaspect.csvparser.dal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Writer {
    private String fileName;
    private String content;

    public Writer(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    public void write() throws IOException {
        String fullFileName = fileName + System.currentTimeMillis() + ".csv";
        Files.write(Paths.get(fullFileName), content.getBytes(StandardCharsets.UTF_8));
    }

}