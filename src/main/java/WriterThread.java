import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {

    private final BlockingQueue<Map.Entry<String, Set<String>>> queue;
    private final String path;

    public WriterThread(BlockingQueue<Map.Entry<String, Set<String>>> queue, String path) {
        this.queue = queue;
        this.path = path;
    }

    @Override
    public void run() {
        System.out.printf("Writer %s started... \n", Thread.currentThread().getName());

        while(true) {
            if (queue.size()==0) {
                System.out.printf("Writer %s finished... \n", Thread.currentThread().getName());
                return;
            }

            try {
                Map.Entry<String, Set<String>> entry = queue.take();
                File file = FileProcessor.createFile(entry.getKey(), path);
                if(file!=null) {
                    FileProcessor.writeToFile(file, entry.getValue());
                }
                System.out.println("Writer " + Thread.currentThread().getName() + " created file:  " + entry.getKey());
                Thread.sleep(150);
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}