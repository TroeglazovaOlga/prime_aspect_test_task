import java.io.IOException;
import java.util.*;

public final class SingletoneMap {

    private static SingletoneMap instance;
    private final Map<String, Set<String>> fileMap;

    private SingletoneMap() {
        this.fileMap = new HashMap<>();
    }

    public static SingletoneMap getInstance() {
        if (instance == null) {
            instance = new SingletoneMap();
        }
        return instance;
    }

    public void setEntry(String fileName, String value){
        if(this.fileMap.containsKey(fileName)){
            fileMap.get(fileName).add(value);
        }
        else{
            Set<String> valuesSet = new HashSet<>();
            valuesSet.add(value);
            fileMap.put(fileName, valuesSet);
        }
    }

    public void setMap(Map<String, Set<String>> map){
        for (String key: map.keySet()) {
            for(String value: map.get(key))
                this.setEntry(key, value);
        }
    }

    public void convertToFiles(String pathToDir) throws IOException {
        for (String key: fileMap.keySet()) {
            FileProcessor.createFile(key, fileMap.get(key), pathToDir);
        }
    }

}
