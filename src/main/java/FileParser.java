import dal.Reader;
import dal.Writer;
import parserservice.Parser;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileParser {
    private Reader reader;
    private Parser parser;

    public FileParser(Reader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public void parseFile(File file, String pathToWrite) {
        try {
            String content = reader.read(file);
            Map<String, Set<String>> map = parser.parse(content);
            List<Thread> writers = new ArrayList<>();

            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                writers.add(new Thread(() -> {
                    try {
                        new Writer(entry.getKey(), entry.getValue()).write(pathToWrite);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ));
            }
            writers.forEach(Thread::start);
            for (Thread thread : writers) {
                thread.join();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String pathToReadFiles = "src/main/resources/read/";
        String pathToWriteFiles = "src/main/resources/write/";

        File dir = new File(pathToReadFiles);
        if (dir.length() > 0) {
            File[] listFiles = Objects.requireNonNull(new File(pathToReadFiles).listFiles());
            List<Thread> readers = new ArrayList<>();

            for (File file : listFiles) {
                readers.add(new Thread(() -> {
                    new FileParser(new Reader(), new Parser()).parseFile(file, pathToWriteFiles);
                }));
            }
            readers.forEach(Thread::start);

            try {
                for (Thread thread : readers) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("Папка пуста");
        }
    }

}