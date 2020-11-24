import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ReaderThread implements Runnable {

    private final BlockingQueue<File> files;

    public ReaderThread(BlockingQueue<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        System.out.printf("Reader %s started... \n", Thread.currentThread().getName());

        while(true) {
            if (files.size()==0) {
                System.out.printf("Reader %s finished... \n", Thread.currentThread().getName());
                return;
            }

            try {
                File file = files.take();
                if (file.isFile()) {
                    SingletonMap map = SingletonMap.getInstance();
                    map.setMap(FileProcessor.parseFile(file));
                    System.out.println("Reader " + Thread.currentThread().getName() + " process file:  " + file.getName());
                }
                Thread.sleep(150);
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}