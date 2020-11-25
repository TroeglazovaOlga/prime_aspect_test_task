import java.io.File;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProcessFilesDemo {

    public static void main(String[] args) {
        String pathToReadFiles = args[0];
        String pathToWriteFiles = args[1];

        int readerCount = 3;
        int writerCount = 3;
        Thread[] reader = new Thread[readerCount];
        Thread[] writer = new Thread[writerCount];

        File[] listFiles = new File(pathToReadFiles).listFiles();
        final BlockingQueue<File> queueToReadFiles = new LinkedBlockingDeque<>(Arrays.asList(listFiles != null ? listFiles : new File[0]));
        final BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles = new LinkedBlockingDeque<>();

        for (int i = 0; i < readerCount; i++) {
            reader[i] = new Thread(new ReaderThread(queueToReadFiles, queueToWriteFiles));
        }
        Arrays.stream(reader).forEach(Thread::start);

        for (int i = 0; i < writerCount; i++) {
            writer[i] = new Thread(new WriterThread(queueToWriteFiles, pathToWriteFiles));
        }
        Arrays.stream(writer).forEach(Thread::start);

        try{
            for (Thread thread : reader) {
                thread.join();
            }
            Map<String, Set<String>> exitLoopForThread = new HashMap<>();
            exitLoopForThread.put("exit loop", new HashSet<>());
            for(int i = 0; i<writerCount; i++){
                queueToWriteFiles.put(exitLoopForThread.entrySet().iterator().next());
            }
            for (Thread thread : writer) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
