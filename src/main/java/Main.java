import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);
    static String pathStart = new String("C:/Users/emree/Desktop/rep/start.xlsx");
    static String pathFinish = new String("C:/Users/emree/Desktop/rep/finish.xlsx");
    static String pathRepository = new String("C:/Users/emree/Desktop/rep/repository.xlsx");


    public static void main(String[] args) {
        Read readClass = new Read();
        Map<String, Double> readedGoodsMap = readClass.getMapGoods(pathStart);
        Read readClass2 = new Read();
        Map<String, Double> repositoryMap = readClass2.getMapGoods(pathRepository);
        Repository repository = new Repository();
        Map<String, Double> sortedMap = repository.sortReposotory(readedGoodsMap, repositoryMap);
        Write writeClass = new Write();
        writeClass.writeGoodsMap(pathFinish, sortedMap);

    }
}