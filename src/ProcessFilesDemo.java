import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProcessFilesDemo {
    public static void main(String[] args) throws IOException {

        String pathToFiles = "/home/olga/Desktop/test";
        int readerCount = 4;
        Thread[] reader = new Thread[readerCount];

        File folder = new File(pathToFiles);
        final BlockingQueue<File> queue = new LinkedBlockingDeque<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));

        for (int i = 0; i < readerCount; i++){
            reader[i] = new Thread(new ReaderThread(queue));
        }
        Arrays.stream(reader).forEach(Thread::start);

        SingletoneMap map = SingletoneMap.getInstance();

        try{
            for(Thread thread : reader){
                thread.join();
            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

        map.convertToFiles("/home/olga/Desktop/test/t");

    }
}
