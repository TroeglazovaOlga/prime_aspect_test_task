import java.io.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

public class FileProcessor {

    public static File createFile(String fileName, String pathToDirectory) throws IOException {
        File dir = new File(pathToDirectory);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fullFileName = pathToDirectory + File.separator + fileName + ".csv";
        File file = new File(fullFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    //считываем с файла строку со значениями, добавляем их в Set, который передали в параметре и перезаписываем файл.
    public static void writeToFile(RandomAccessFile file, Set<String> values) throws IOException {
        String oldValues = file.readLine();
        Set<String> valuesSet = new HashSet<>();

        if (oldValues!=null) {
            valuesSet.addAll(Arrays.asList(oldValues.split(";")));
        }
        valuesSet.addAll(values);

        file.setLength(0);
        for(String value: valuesSet) {
            file.write((value + ";").getBytes(UTF_8));
        }
    }

    //Считываем первую строку файла (названия столбцов), это будут названия для будущих файлов. Далее построчно считываем остальное и заполняем Map.
    //Каждый его объект - пара значений, где ключ - название столбца, а значение - Set из строк, стоящих в соответствующем столбце исходного файла.
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
