package dal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Reader {

    public String read(File file) throws IOException {
        return String.join("\n", Files.readAllLines(Path.of(file.getAbsolutePath())));
    }

}