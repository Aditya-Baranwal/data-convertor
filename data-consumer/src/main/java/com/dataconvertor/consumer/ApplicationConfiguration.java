package com.dataconvertor.consumer;

import com.dataconvertor.common.enums.Operation;
import com.dataconvertor.consumer.impl.processor.AddProcess;
import com.dataconvertor.consumer.impl.processor.DivideProcess;
import com.dataconvertor.consumer.impl.processor.MultiplyProcess;
import com.dataconvertor.consumer.impl.processor.SubProcess;
import com.dataconvertor.consumer.impl.writer.CSVWriter;
import com.dataconvertor.consumer.impl.writer.DBWriter;
import com.dataconvertor.consumer.impl.writer.XLSXWriter;
import com.dataconvertor.consumer.interfaces.DataProcessor;
import com.dataconvertor.consumer.interfaces.DataWriter;
import com.dataconvertor.common.enums.Destination;
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

    @Autowired
    AddProcess addProcess;

    @Autowired
    SubProcess subProcess;

    @Autowired
    MultiplyProcess multiplyProcess;

    @Autowired
    DivideProcess divideProcess;

    HashMap<Destination, DataWriter> getDestinationWriterConfig() {
        HashMap<Destination, DataWriter> Destination_Writer_Map = new HashMap<>();
        Destination_Writer_Map.put(Destination.DB, dbWriter);
        Destination_Writer_Map.put(Destination.CSV, csvWriter);
        Destination_Writer_Map.put(Destination.XLSX, xlsxWriter);
        return  Destination_Writer_Map;
    }

    HashMap<Operation, DataProcessor> getOperationProcessorConfig() {
        HashMap<Operation, DataProcessor> Operation_Processor_Map = new HashMap<>();
        Operation_Processor_Map.put(Operation.ADD, addProcess);
        Operation_Processor_Map.put(Operation.SUB, subProcess);
        Operation_Processor_Map.put(Operation.DIV, divideProcess);
        Operation_Processor_Map.put(Operation.MUL, multiplyProcess);
        return  Operation_Processor_Map;
    }

}
