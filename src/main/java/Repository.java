import java.util.HashMap;
import java.util.Map;

public class Repository {

    public Map<String, Double> sortReposotory(Map<String, Double> mapAll, Map<String, Double> mapReport){
        Map<String, Double> sortedhMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : mapAll.entrySet()) {
            if(mapReport.containsKey(entry.getKey()))
                sortedhMap.put(entry.getKey(), entry.getValue());
        }

        return sortedhMap;
    }
}
