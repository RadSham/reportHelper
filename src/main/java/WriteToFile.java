import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class WriteToFile extends FindColumn {

    private final Map<String, Integer> columns = new HashMap<>();

    public void writeExist(String path, Map<String, Double> finishMap) {
        try {
            FileInputStream file = new FileInputStream(path);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            findColumn(sheet, columns, "Наименование для отчета");
            findColumn(sheet, columns, "ШТ");
            findColumn(sheet, columns, "Отчет");

            for (Row row : sheet) {
                Cell cell = row.getCell(columns.get("Наименование для отчета"));
                if (cell != null) {
                    for (Map.Entry<String, Double> entry : finishMap.entrySet()) {
                        if (cell.getStringCellValue().equals(entry.getKey())) {
                            Cell cellRep = row.createCell(columns.get("Отчет"), CellType.NUMERIC);
                            cellRep.setCellValue(entry.getValue());
                        }
                    }
                }
            }

            file.close();

            FileOutputStream outFile = new FileOutputStream(path);
            workbook.write(outFile);
            outFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
