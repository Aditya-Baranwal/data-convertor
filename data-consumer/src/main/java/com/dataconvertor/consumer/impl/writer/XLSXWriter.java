package com.dataconvertor.consumer.impl.writer;

import com.dataconvertor.consumer.interfaces.DataWriter;
import com.dataconvertor.consumer.dao.OperationDao;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Component
public class XLSXWriter implements DataWriter {

    private final ObjectMapper jsonObjectMapper;

    public XLSXWriter() {
        this.jsonObjectMapper = new ObjectMapper();
    }
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
//                OutputStream fileOut = new FileOutputStream("operations.xlsx");
                OutputStream fileOut = new FileOutputStream(filePath);
                sheet = wb.createSheet("sheet-1");
                JsonNode jsonObject = this.jsonObjectMapper.valueToTree(operationResult);
                Iterator<Map.Entry<String, JsonNode>> fields = jsonObject.fields();
                Row r = sheet.createRow(0);
                int i = 0;
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    r.createCell(i++).setCellValue(field.getKey());
                }
                wb.write(fileOut);
                fileOut.close();
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
