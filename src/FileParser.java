import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileParser {

    public static Map<String, Set<String>> parseFile(File file) throws IOException {
        Map<String, Set<String>> map = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {

            String[] fileNames = br.readLine().split(";");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                for(int i = 0; i < values.length; i++){
                    if(map.containsKey(fileNames[i])){
                        map.get(fileNames[i]).add(values[i]);
                    }
                    else{
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
