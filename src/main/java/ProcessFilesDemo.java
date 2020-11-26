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
        //если файлов для чтения не существует, создадим пустой массив из файлов, чтобы программа корректно отработала
        final BlockingQueue<File> queueToReadFiles = new LinkedBlockingDeque<>(Arrays.asList(listFiles != null ? listFiles : new File[0]));
        final BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles = new LinkedBlockingDeque<>();

        //создаем потоки для чтения и парсинга файлов и запускаем
        for (int i = 0; i < readerCount; i++) {
            reader[i] = new Thread(new ReaderThread(queueToReadFiles, queueToWriteFiles));
        }
        Arrays.stream(reader).forEach(Thread::start);

        //создаем потоки для создания (открытия) файлов и для записи и запускаем
        for (int i = 0; i < writerCount; i++) {
            writer[i] = new Thread(new WriterThread(queueToWriteFiles, pathToWriteFiles));
        }
        Arrays.stream(writer).forEach(Thread::start);

        try {
            //ждем завершения потоков - читателей
            for (Thread thread : reader) {
                thread.join();
            }

            //кладем в очередь еще столько задач, сколько потоков - писателей. При встрече с такой задачей они выйдут из цикла
            Map<String, Set<String>> exitLoopForThread = new HashMap<>();
            exitLoopForThread.put("exit loop", new HashSet<>());
            for (int i = 0; i<writerCount; i++) {
                queueToWriteFiles.put(exitLoopForThread.entrySet().iterator().next());
            }

            //ждем завершения потоков - писателей
            for (Thread thread : writer) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
