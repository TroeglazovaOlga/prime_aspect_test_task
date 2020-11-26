import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {

    private final BlockingQueue<Map.Entry<String, Set<String>>> queue; // очередь из пар: название файла - содержимое
    private final String path; //путь к этим файлам

    public WriterThread(BlockingQueue<Map.Entry<String, Set<String>>> queue, String path) {
        this.queue = queue;
        this.path = path;
    }

    @Override
    public void run() {
        System.out.printf("Writer %s started... \n", Thread.currentThread().getName());

        while(true) {
            try {
                Map.Entry<String, Set<String>> entry = queue.take();

                //условие выхода из цикла
                if (entry.getKey().equals("exit loop")) {
                    System.out.printf("Writer %s finished... \n", Thread.currentThread().getName());
                    return;
                }

                File file = FileProcessor.createFile(entry.getKey(), path);

                //открываем файл и блокируем доступ к нему остальными потоками
                try (RandomAccessFile raf = new RandomAccessFile(String.valueOf(file),  "rw");
                     FileChannel channel = raf.getChannel();
                     FileLock lock = channel.lock()) {
                    System.out.println("Writer " + Thread.currentThread().getName() + " opened file:  " + entry.getKey());
                    FileProcessor.writeToFile(raf, entry.getValue());
                }

                Thread.sleep(150);
            } catch (InterruptedException | IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

}