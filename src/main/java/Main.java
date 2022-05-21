import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);
    static String pathTotalGoods = "C:/Users/emree/Desktop/rep/Orders.xlsx";
    static String pathReportGoods = "C:/Users/emree/Desktop/rep/report.xlsx";

    public static void main(String[] args) {
        Read readClass = new Read();
        Map<String, Double> totalGoodsMap = readClass.getGoodsMap(pathTotalGoods);
        Map<String, Double> reportGoodsMap = readClass.getGoodsMap(pathReportGoods);

        Repository repository = new Repository();
        Map<String, Double> finishMap = repository.sortReposotory(totalGoodsMap, reportGoodsMap);

        WriteToFile writeToFile = new WriteToFile();
        writeToFile.writeExist(pathReportGoods, finishMap);

    }
}