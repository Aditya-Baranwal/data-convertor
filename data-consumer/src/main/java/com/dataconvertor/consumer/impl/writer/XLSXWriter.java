package com.dataconvertor.consumer.impl.writer;

import com.dataconvertor.consumer.interfaces.DataWriter;
import com.dataconvertor.consumer.dao.OperationDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class XLSXWriter implements DataWriter {

    @Value("${spring.config.path.xlsx}")
    String filePath;

    @Override
    public void write(OperationDao operationResult) {
        try {
            File excelFile = new File(filePath);
            XSSFWorkbook wb;
            XSSFSheet sheet;
            if(!excelFile.exists())  {
                wb = new XSSFWorkbook();
                OutputStream fileOut = new FileOutputStream("operations.xlsx");
                sheet = wb.createSheet("operation-output");
                String[] headers = new String[] { "Number-1", "Number-2", "Operation", "Result" };
                Row r = sheet.createRow(0);
                for (int cn=0; cn<headers.length; cn++) {
                    r.createCell(cn).setCellValue(headers[cn]);
                }
                wb.write(fileOut);
                fileOut.close();
                System.out.println("operation excel sheet not found, excel sheet has been created successfully");
            } else {
                FileInputStream fileIn = new FileInputStream(excelFile);
                wb = new XSSFWorkbook(fileIn);
                sheet = wb.getSheetAt(0);
                fileIn.close();
            }
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
            wb.write(fos);
            wb.close();
            fos.close();
        } catch (IOException fe) {
            fe.printStackTrace();
        }
    }
}
