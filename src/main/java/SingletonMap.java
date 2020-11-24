import java.util.*;

public final class SingletonMap {

    private static SingletonMap instance;
    private final Map<String, Set<String>> fileMap;

    private SingletonMap() {
        this.fileMap = new HashMap<>();
    }

    public static SingletonMap getInstance() {
        if (instance == null) {
            instance = new SingletonMap();
        }
        return instance;
    }

    public void setEntry(String fileName, String value) {
        if (this.fileMap.containsKey(fileName)) {
            fileMap.get(fileName).add(value);
        }
        else {
            Set<String> valuesSet = new HashSet<>();
            valuesSet.add(value);
            fileMap.put(fileName, valuesSet);
        }
    }

    public void setMap(Map<String, Set<String>> map) {
        for (String key: map.keySet()) {
            for(String value: map.get(key))
                this.setEntry(key, value);
        }
    }

    public Queue<Map.Entry<String, Set<String>>> toQueue() {
        return new LinkedList<>(fileMap.entrySet());
    }

}
