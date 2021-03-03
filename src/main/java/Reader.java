import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Reader implements Runnable {

    private final BlockingQueue<File> queueToReadFiles; // очередь из необработанных файлов для чтения
    private final BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles; //очередь из пар название файла - содержимое для записи

    public Reader(BlockingQueue<File> queueToReadFiles) {
        this.queueToReadFiles = queueToReadFiles;
        this.queueToWriteFiles = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {
        System.out.printf("Reader %s started... \n", Thread.currentThread().getName());

        while(true) {
            if (queueToReadFiles.size()==0) {
                System.out.printf("Reader %s finished... \n", Thread.currentThread().getName());
                return;
            }

            try {
                File file = queueToReadFiles.take();

                if (file.isFile()) {
                    String content = read(file);
                    Map<String, Set<String>> map = Parser.parse(content);
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()) {
                        queueToWriteFiles.put(entry);
                    }
                    System.out.println("Reader " + Thread.currentThread().getName() + " process file:  " + file.getName());
                }

                int writerCount = queueToWriteFiles.size();
                Thread[] writer = new Thread[writerCount];

                for (int i = 0; i < writerCount; i++) {
                    Map.Entry<String, Set<String>> entry = queueToWriteFiles.take();
                    writer[i] = new Thread(new Writer(entry.getKey(), entry.getValue()));
                }
                Arrays.stream(writer).forEach(Thread::start);
                for (Thread thread : writer) {
                    thread.join();
                }
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String read(File file) throws IOException {
        return new String(Files.readAllBytes(Path.of("src/main/resources/read/" + file.getName())));
    }
}