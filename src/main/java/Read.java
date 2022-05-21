import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class Read extends FindColumn{
    private static final Logger log = getLogger(Read.class);

    private final Map<String, Integer> columns = new HashMap<>();

    private List<Goods> read(String path) {
        List<Goods> goodsList = new ArrayList();

        FileInputStream inputStream;
        XSSFWorkbook wbStart = null;

        try {
            inputStream = new FileInputStream(path);
            wbStart = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert wbStart != null;
        XSSFSheet sheet = wbStart.getSheetAt(0);
        findColumn(sheet,columns, "Наименование для отчета");
        findColumn(sheet,columns, "ШТ");

        for (Row row : sheet) {
            Goods good = new Goods();

            //searching in special columns
            for (Map.Entry<String, Integer> entry : columns.entrySet()) {

                Cell cell = row.getCell(entry.getValue());
                //does not read reportGoods quantity
                if (cell == null) break;
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case _NONE:
                    case BLANK:
                    case BOOLEAN:
                    case ERROR:
                        good.setNumber(0.0);
                    case FORMULA:
                        FormulaEvaluator evaluator = wbStart.getCreationHelper().createFormulaEvaluator();
                        if(evaluator.evaluateFormulaCell(cell)==NUMERIC) good.setNumber(cell.getNumericCellValue());
                        break;
                    case NUMERIC:
                        good.setNumber(cell.getNumericCellValue());
                        break;
                    case STRING:
                        if (cell.getColumnIndex() == columns.get("ШТ")) break;
                        good.setName(cell.getStringCellValue());
                        break;
                }
            }
            goodsList.add(good);
        }
        return goodsList;
    }

    public Map<String, Double> getGoodsMap(String path) {
        Map<String, Double> mapGoods = new HashMap<>();
        List<Goods> list = read(path);
        for (Goods g : list) {

            if (g.getName() != null && g.getNumber() != null) {
                mapGoods.merge(g.getName(), g.getNumber(), Double::sum);
            }
        }
        return mapGoods;
    }


}
