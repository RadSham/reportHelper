import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);
    static String pathTotalGoods = "C:/Users/emree/Desktop/rep/Orders.xlsx";
    static String pathReportGoods = "C:/Users/emree/Desktop/rep/reportGoods.xlsx";
    static String pathFinishReport = "C:/Users/emree/Desktop/rep/finishReport.xlsx";


    public static void main(String[] args) {
        Read readClass = new Read();
        Map<String, Double> totalGoodsMap = readClass.getGoodsMap(pathTotalGoods);
        Map<String, Double> reportGoodsMap = readClass.getGoodsMap(pathReportGoods);

        Repository repository = new Repository();
        Map<String, Double> finishMap = repository.sortReposotory(totalGoodsMap, reportGoodsMap);

        Write writeClass = new Write();
        writeClass.writeGoodsMap(pathFinishReport, finishMap);
    }
}