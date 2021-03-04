import dal.Reader;
import dal.Writer;
import parserservice.Parser;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class FileParser {
    private Reader reader;
    private Parser parser;

    public FileParser(Reader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public void parseFile(String fileName) {
        try {
            String content = reader.read(fileName);
            Map<String, Set<String>> map = parser.parse(content);
            map.forEach((name, fileContent) -> {
                Thread writeThread = new Thread(() -> {
                    try {
                        new Writer(name, fileContent).write();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writeThread.start();
            });
        } catch (NoSuchFileException e) {
            System.out.println("Файл " + fileName + " не найден");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}