import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public class FindColumn {
    void findColumn(Sheet sheet, Map<String, Integer> columns, String name) {
        Row row = sheet.getRow(1);
        for (Cell cell : row) {
            if (cell.getStringCellValue().equals(name)) {
                columns.put(name, cell.getColumnIndex());
            }
        }
    }
}
