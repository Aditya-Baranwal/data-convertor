package com.dataconvertor.consumer.impl;

import com.dataconvertor.consumer.interfaces.DataWriter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.dataconvertor.consumer.dao.OperationDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class CSVWriter implements DataWriter {

    @Value("${application.config.path.csv}")
    Path filePath;

    @Override
    public void write(OperationDao operationResult) {
        try (FileOutputStream fos = new FileOutputStream(filePath.toString(), true)){
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
        }
    }
}
