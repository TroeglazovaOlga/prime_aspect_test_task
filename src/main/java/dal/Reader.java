package dal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {

    public String read(String fullFileName) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(fullFileName)));
    }

}