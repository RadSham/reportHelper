import java.util.HashMap;
import java.util.Map;

public class Repository {

    private Map<String, Double> finishMap;

    public Map<String, Double> sortReposotory(Map<String, Double> mapAll, Map<String, Double> mapReport){
        finishMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : mapAll.entrySet()) {
            if(mapReport.containsKey(entry.getKey()))
                finishMap.put(entry.getKey(), entry.getValue());
        }
        System.out.println(finishMap);
        return finishMap;
    }
}
