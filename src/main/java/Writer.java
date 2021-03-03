import java.io.*;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Writer implements Runnable {

    private String fileName;
    private Set<String> content;
    private final String path = "src/main/resources/write/"; //путь к этим файлам

    public Writer(String fileName, Set<String> content) {
        this.fileName = fileName;
        this.content = content;
    }

    @Override
    public void run() {
        System.out.printf("Writer %s started... \n", Thread.currentThread().getName());
        try {
            write(fileName, content);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    //считываем с файла строку со значениями, добавляем их в Set, который передали в параметре и перезаписываем файл.
    private void write(String fileName, Set<String> values) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fullFileName = path + File.separator + fileName + ' ' + System.currentTimeMillis() + ".csv";
        File file = new File(fullFileName);
        file.createNewFile();
        try (RandomAccessFile raf = new RandomAccessFile(String.valueOf(file),  "rw")) {
            for(String value: values) {
                raf.write((value + ";").getBytes(UTF_8));
            }
        }
    }

}