import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Read {
    private static final Logger log = getLogger(Read.class);

    Map<String, Double> mapGoods = new HashMap<>();
    List<Goods> goodsList;

    public List<Goods> read(String path) {

        goodsList = new ArrayList();

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

        for (Row row : sheet) {
            Goods good = new Goods();
            // Get iterator to all cells of current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case _NONE:
                    case BLANK:
                    case BOOLEAN:
                    case ERROR:
                        break;
                    /*case FORMULA:
                        // Formula
                        System.out.print(cell.getCellFormula());
                        System.out.print("\t");

                        FormulaEvaluator evaluator = wbStart.getCreationHelper().createFormulaEvaluator();
                        System.out.print(evaluator.evaluate(cell).getNumberValue());
                        break;*/
                    case NUMERIC:
                        good.setNumber(cell.getNumericCellValue());
                        break;
                    case STRING:
                        if (cell.getColumnIndex() == 1) break;
                        good.setName(cell.getStringCellValue());
                        break;
                }

            }
            goodsList.add(good);
        }
        return goodsList;
    }

    public Map<String, Double> getMapGoods(String path) {
        List<Goods> list = read(path);
        for (Goods g : list) {
            if (g.getName() != null && g.getNumber() != null) {
                mapGoods.merge(g.getName(), g.getNumber(), Double::sum);
            }
        }
        return mapGoods;
    }
}
