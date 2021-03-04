package dal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class Writer {
    private String fileName;
    private Set<String> content;

    public Writer(String fileName, Set<String> content) {
        this.fileName = fileName;
        this.content = content;
    }

    public void write() throws IOException {
        String fullFileName = fileName + System.currentTimeMillis() + ".csv";
        Files.write(Paths.get(fullFileName), String.join(";", content).getBytes(StandardCharsets.UTF_8));
    }

}