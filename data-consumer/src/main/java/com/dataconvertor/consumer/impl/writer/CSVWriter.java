package com.dataconvertor.consumer.impl.writer;

import com.dataconvertor.common.enums.Operation;
import com.dataconvertor.consumer.dao.OperationDao;
import com.dataconvertor.consumer.interfaces.DataWriter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


@Component
public class CSVWriter implements DataWriter {

    private final CsvMapper csvMapper;

    private final ObjectMapper jsonObjectMapper;

    public CSVWriter() {
        this.csvMapper = new CsvMapper();
        this.csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        this.jsonObjectMapper = new ObjectMapper();
    }

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

            JsonNode jsonObject = this.jsonObjectMapper.valueToTree(operationResult);

            // schema for csv file
            CsvSchema.Builder builder = CsvSchema.builder().setUseHeader(true);
            Iterator<Map.Entry<String, JsonNode>> fields = jsonObject.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                builder.addColumn(field.getKey());
            }
            CsvSchema schema = builder.build();

            // write to file
            ObjectWriter writer = this.csvMapper.writerFor(OperationDao.class).with(schema);
            writer.writeValues(fos).write(operationResult);

        } catch (IOException fe) {
            fe.printStackTrace();
            throw new RuntimeException(fe);
        }
    }
}










