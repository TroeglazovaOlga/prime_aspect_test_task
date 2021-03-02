import java.io.File;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProcessFilesDemo {

    public static void main(String[] args) {
        String pathToReadFiles = "src/main/resources/read/";
        File[] listFiles = new File(pathToReadFiles).listFiles();

        int readerCount = listFiles.length;
        Thread[] reader = new Thread[readerCount];

        //если файлов для чтения не существует, создадим пустой массив из файлов, чтобы программа корректно отработала
        final BlockingQueue<File> queueToReadFiles = new LinkedBlockingDeque<>(Arrays.asList(listFiles != null ? listFiles : new File[0]));

        //создаем потоки для чтения файлов и запускаем
        for (int i = 0; i < readerCount; i++) {
            reader[i] = new Thread(new Reader(queueToReadFiles));
        }
        Arrays.stream(reader).forEach(Thread::start);

        try {
            //ждем завершения потоков - читателей
            for (Thread thread : reader) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
