import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileProcessor {

    public static File createFile(String fileName, String pathToDirectory) throws IOException {
        File dir = new File(pathToDirectory);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String separator = System.getProperty("os.name").equals("Linux") ? "/" : "\\";
        String fullFileName = pathToDirectory + separator + fileName + ".csv";

        File file = new File(fullFileName);
        if (file.exists()) {
            file.delete();
        }

        boolean created = file.createNewFile();
        return created ? file : null;
    }

    public static void writeToFile(File file, Set<String> values) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             PrintStream printStream = new PrintStream(fos)) {
            for (String value: values) {
                printStream.print(value + ";");
            }
        }
    }

    public static Map<String, Set<String>> parseFile(File file) throws IOException {
        Map<String, Set<String>> map = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {

            String[] fileNames = br.readLine().split(";");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                for (int i = 0; i < values.length; i++) {
                    if (map.containsKey(fileNames[i])){
                        map.get(fileNames[i]).add(values[i]);
                    }
                    else {
                        Set<String> valuesSet = new HashSet<>();
                        valuesSet.add(values[i]);
                        map.put(fileNames[i], valuesSet);
                    }
                }
            }

        }
        return map;
    }

}
