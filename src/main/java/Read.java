import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Read {
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
        findColumn(sheet, "Наименование");
        findColumn(sheet, "ШТ");
        System.out.println(path + columns);

        for (Row row : sheet) {
            Goods good = new Goods();
            // Get iterator to all cells of current row
            for (Map.Entry<String, Integer> entry : columns.entrySet()) {

                Cell cell = row.getCell(entry.getValue());
                if (cell == null) break;
                CellType cellType = null;
                try {
                    cellType = cell.getCellType();
                } catch (Exception IO) {
                    System.out.println(IO);
                }

                switch (cellType) {
                    case _NONE:
                    case BLANK:
                    case BOOLEAN:
                    case ERROR:
                    case FORMULA:
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

    private void findColumn(Sheet sheet, String name) {
        Row row = sheet.getRow(1);
        for (Cell cell : row) {
            if (cell.getStringCellValue().equals(name)) {
                columns.put(name, cell.getColumnIndex());
            }
        }
    }

}
