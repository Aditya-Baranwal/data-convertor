package com.dataconvertor.consumer.impl.writer;

import com.dataconvertor.consumer.dao.OperationDao;
import com.dataconvertor.consumer.interfaces.DataWriter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CSVWriter implements DataWriter {

    @Value("${spring.config.path.csv}")
    String filePath;

    @Override
    public void write(OperationDao operationResult) {

        File operationFile = new File(filePath);
        if(!operationFile.exists()) {
            try {
                operationFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
            // mapper will map pojo according to json.
            CsvMapper mapper = new CsvMapper();
            mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

            // schema for csv file
            CsvSchema schema = CsvSchema.builder().build();

            // write to file
            ObjectWriter writer = mapper.writerFor(OperationDao.class).with(schema);
            writer.writeValues(fos).write(operationResult);
        } catch (IOException fe) {
            fe.printStackTrace();
            throw new RuntimeException(fe);
        }
    }
}
