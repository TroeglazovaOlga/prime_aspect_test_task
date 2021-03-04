package dal;

import java.io.*;
import java.util.Set;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Writer {
    private String fileName;
    private Set<String> content;

    public Writer(String fileName, Set<String> content) {
        this.fileName = fileName;
        this.content = content;
    }

    public void write(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fullFileName = path + File.separator + fileName + ' ' + System.currentTimeMillis() + ".csv";
        File file = new File(fullFileName);
        file.createNewFile();
        try (RandomAccessFile raf = new RandomAccessFile(String.valueOf(file),  "rw")) {
            for(String value: content) {
                raf.write((value + ";").getBytes(UTF_8));
            }
        }
    }

}