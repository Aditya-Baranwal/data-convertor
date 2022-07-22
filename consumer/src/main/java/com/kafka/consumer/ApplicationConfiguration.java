package com.kafka.consumer;

import com.kafka.consumer.enums.Destination;
import com.kafka.consumer.impl.CSVWriter;
import com.kafka.consumer.impl.DBWriter;
import com.kafka.consumer.impl.XLSXWriter;
import com.kafka.consumer.interfaces.DataWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Component
@Configuration
public class ApplicationConfiguration {

    @Autowired
    CSVWriter csvWriter;

    @Autowired
    DBWriter dbWriter;

    @Autowired
    XLSXWriter xlsxWriter;

    HashMap<Destination, DataWriter> getDestinationWriterConfig() {
        HashMap<Destination, DataWriter> Destination_Writer_Map = new HashMap<>();
        Destination_Writer_Map.put(Destination.DB, dbWriter);
        Destination_Writer_Map.put(Destination.CSV, csvWriter);
        Destination_Writer_Map.put(Destination.XLSX, xlsxWriter);
        return  Destination_Writer_Map;
    }

}
