import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProcessFilesDemo {

    public static void main(String[] args) {
        String pathToReadFiles = args[0];
        String pathToWriteFiles = args[1];

        int readerCount = 4;
        int writerCount = 4;
        Thread[] reader = new Thread[readerCount];
        Thread[] writer = new Thread[writerCount];

        File[] listFiles = new File(pathToReadFiles).listFiles();
        final BlockingQueue<File> queueToReadFiles = new LinkedBlockingDeque<>(Arrays.asList(listFiles != null ? listFiles : new File[0]));

        for (int i = 0; i < readerCount; i++) {
            reader[i] = new Thread(new ReaderThread(queueToReadFiles));
        }
        Arrays.stream(reader).forEach(Thread::start);

        try {
            for (Thread thread : reader) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        final BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles = new LinkedBlockingDeque<>(SingletonMap.getInstance().toQueue());

        for (int i = 0; i < writerCount; i++) {
            writer[i] = new Thread(new WriterThread(queueToWriteFiles, pathToWriteFiles));
        }
        Arrays.stream(writer).forEach(Thread::start);

        try{
            for (Thread thread : writer) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
