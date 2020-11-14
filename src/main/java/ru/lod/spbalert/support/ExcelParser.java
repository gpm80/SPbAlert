package ru.lod.spbalert.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lod.spbalert.dao.AlertDocument;
import ru.lod.spbalert.model.SpbAlert;

public class ExcelParser {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParser.class);

    public static List<AlertDocument> parse(InputStream inputStream) {
        final List<AlertDocument> alertDocuments = new ArrayList<AlertDocument>();
        try (Workbook excelBook = WorkbookFactory.create(inputStream)) {
            final Sheet sheet = excelBook.getSheetAt(0);
            final Iterator<Row> rowIterator = sheet.rowIterator();
            // Пропускаем заголовки колонок
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            rowIterator.forEachRemaining(cells -> {
                final AlertDocument alertDocument = new AlertDocument();
                Optional.ofNullable(cells.getCell(1)).ifPresent(cell -> alertDocument.setCategory(cell.getStringCellValue()));
                final SpbAlert spbAlert = new SpbAlert();
                Optional.ofNullable(cells.getCell(0)).ifPresent(cell -> spbAlert.setDate(cell.getDateCellValue()));
                Optional.ofNullable(cells.getCell(2)).ifPresent(cell -> spbAlert.setAddressEac(cell.getNumericCellValue()));
                Optional.ofNullable(cells.getCell(3)).ifPresent(cell -> spbAlert.setBuildingEac(cell.getNumericCellValue()));
                Optional.ofNullable(cells.getCell(4)).ifPresent(cell -> spbAlert.setLatitude(cell.getNumericCellValue()));
                Optional.ofNullable(cells.getCell(5)).ifPresent(cell -> spbAlert.setLongitude(cell.getNumericCellValue()));
                Optional.ofNullable(cells.getCell(6)).ifPresent(cell -> spbAlert.setDistrict(cell.getStringCellValue()));
                alertDocument.setSpbAlert(spbAlert);
                alertDocuments.add(alertDocument);
            });
        } catch (IOException e) {
            logger.debug(e.getMessage(), e);
        }
        return alertDocuments;
    }

}
