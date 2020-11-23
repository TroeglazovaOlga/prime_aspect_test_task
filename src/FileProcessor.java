import java.io.*;
import java.util.Set;

public class FileProcessor {

    public static File createFile(String fileName, Set<String> content, String pathToDirectory) throws IOException {
        File dir = new File(pathToDirectory);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir.getPath() + "/" + fileName + ".csv");

        boolean created = file.createNewFile();
        if(created){
            try (FileOutputStream fos = new FileOutputStream(file);
                 PrintStream printStream = new PrintStream(fos)) {
                for(String line: content){
                    printStream.println(line + ";");
                }
            }
        }

        return file;
    }

}
