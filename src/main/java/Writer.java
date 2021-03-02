import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.HashSet;
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

            File file = createFile(fileName, path);

            //открываем файл и блокируем доступ к нему остальными потоками
            try (RandomAccessFile raf = new RandomAccessFile(String.valueOf(file),  "rw");
                 FileChannel channel = raf.getChannel();
                 FileLock lock = channel.lock()) {
                System.out.println("Writer " + Thread.currentThread().getName() + " opened file:  " + fileName);
                write(raf, content);
            }

            Thread.sleep(150);
        } catch (InterruptedException | IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    private File createFile(String fileName, String pathToDirectory) throws IOException {
        File dir = new File(pathToDirectory);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fullFileName = pathToDirectory + File.separator + fileName + ".csv";
        File file = new File(fullFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    //считываем с файла строку со значениями, добавляем их в Set, который передали в параметре и перезаписываем файл.
    private void write(RandomAccessFile file, Set<String> values) throws IOException {
        String oldValues = file.readLine();
        Set<String> valuesSet = new HashSet<>();

        if (oldValues!=null) {
            valuesSet.addAll(Arrays.asList(oldValues.split(";")));
        }
        valuesSet.addAll(values);

        file.setLength(0);
        for(String value: valuesSet) {
            file.write((value + ";").getBytes(UTF_8));
        }
    }

}