package com.kafka.consumer.impl;

import com.kafka.consumer.dao.OperationDao;
import com.kafka.consumer.interfaces.DataWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class XLSXWriter implements DataWriter {

    @Value("{application.config.path.xlsx}")
    String filePath;

    @Override
    public void write(OperationDao operationResult) {
        try {
            File excelFile  = new File("C://Users/ADITYA/Desktop/operation.xlsx");
            FileInputStream fis = new FileInputStream(excelFile);

            // create xlxs work book
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);

            // write to sheet
            int rowNumber = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowNumber);
            Object[] objArr = operationResult.toArray();
            int cellNumber = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellNumber++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                }  else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }

            FileOutputStream fos = new FileOutputStream(excelFile);
            book.write(fos);
            book.close();
            fos.close();
            fis.close();
        } catch (IOException fe) {
            fe.printStackTrace();
        }
    }
}
