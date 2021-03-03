import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Reader {

    private final BlockingQueue<File> queueToReadFiles; // очередь из необработанных файлов для чтения
    private final BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles; //очередь из пар название файла - содержимое для записи
    private final String path = "src/main/resources/read/";

    public Reader(BlockingQueue<File> queueToReadFiles) {
        this.queueToReadFiles = queueToReadFiles;
        this.queueToWriteFiles = new LinkedBlockingDeque<>();
    }

    public void read() throws IOException {
        try {
            File file = queueToReadFiles.take();

            if (file.isFile()) {
                String content = new String(Files.readAllBytes(Path.of(path + file.getName())));
                Map<String, Set<String>> map = Parser.parse(content);
                for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                    queueToWriteFiles.put(entry);
                }
                System.out.println("Reader " + Thread.currentThread().getName() + " process file:  " + file.getName());
            }

            int writerCount = queueToWriteFiles.size();
            Thread[] writer = new Thread[writerCount];

            for (int i = 0; i < writerCount; i++) {
                Map.Entry<String, Set<String>> entry = queueToWriteFiles.take();
                writer[i] = new Thread(() -> {
                    try {
                        new Writer(entry.getKey(), entry.getValue()).write();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                );
            }
            Arrays.stream(writer).forEach(Thread::start);
            for (Thread thread : writer) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}