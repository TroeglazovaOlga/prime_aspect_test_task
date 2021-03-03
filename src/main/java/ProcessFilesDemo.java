import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProcessFilesDemo {

    public static void main(String[] args) {
        String pathToReadFiles = "src/main/resources/read/";
        File[] listFiles = new File(pathToReadFiles).listFiles();

        int readerCount = listFiles.length;
        Thread[] reader = new Thread[readerCount];

        final BlockingQueue<File> queueToReadFiles = new LinkedBlockingDeque<>(Arrays.asList(listFiles != null ? listFiles : new File[0]));

        for (int i = 0; i < readerCount; i++) {
            reader[i] = new Thread(() -> {
                try {
                    new Reader(queueToReadFiles).read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            );
        }
        Arrays.stream(reader).forEach(Thread::start);

        try {
            for (Thread thread : reader) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
