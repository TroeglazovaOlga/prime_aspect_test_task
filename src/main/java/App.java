import dal.Reader;
import parserservice.Parser;

public class App {

    public void processFiles(String[] files) {
        for (String fileName : files) {
            new Thread(() -> {
                new FileParser(new Reader(), new Parser()).parseFile(fileName);
            }).start();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.processFiles(args);
    }

}