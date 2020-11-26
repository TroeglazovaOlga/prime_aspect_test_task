import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class ReaderThread implements Runnable {

    private final BlockingQueue<File> queueToReadFiles; // очередь из необработанных файлов для чтения
    private final BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles; //очередь из пар название файла - содержимое для записи

    public ReaderThread(BlockingQueue<File> queueToReadFiles, BlockingQueue<Map.Entry<String, Set<String>>> queueToWriteFiles) {
        this.queueToReadFiles = queueToReadFiles;
        this.queueToWriteFiles = queueToWriteFiles;
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
                    Map<String, Set<String>> map = FileProcessor.parseFile(file);
                    //после обработки файла кладем в очередь каждый элемент из Map отдельно
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()) {
                        queueToWriteFiles.put(entry);
                    }
                    System.out.println("Reader " + Thread.currentThread().getName() + " process file:  " + file.getName());
                }

                Thread.sleep(150);
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}