import java.util.HashMap;
import java.util.Map;

public class Sort {

    public Map<String, Double> sortMaps(Map<String, Double> mapAll, Map<String, Double> mapReport){
        Map<String, Double> sortedMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : mapAll.entrySet()) {
            if(mapReport.containsKey(entry.getKey()))
                sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
