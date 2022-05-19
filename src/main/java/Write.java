import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Write {

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    private void write(String path, List<Goods> readedList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        int rownum = 0;
        Cell cell;
        XSSFRow row;
        //
        XSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // name
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("name");
        cell.setCellStyle(style);

        // number
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("number");
        cell.setCellStyle(style);

        // Data
        for (Goods good : readedList) {
            rownum++;
            row = sheet.createRow(rownum);

            // name
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(good.getName());
            // number
            cell = row.createCell(1, CellType.NUMERIC);
            cell.setCellValue(good.getNumber());

        }


        FileOutputStream outFile;
        try {
            outFile = new FileOutputStream(path);
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeGoodsMap(String path, Map<String, Double> goodsMap) {
        List<Goods> listFromMap = new ArrayList<>();
        for (Map.Entry<String, Double> entry : goodsMap.entrySet()) {
            listFromMap.add(new Goods(entry.getKey(), entry.getValue()));
        }
        write(path, listFromMap);
    }
}
