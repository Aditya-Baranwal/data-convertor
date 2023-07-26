package com.dataconvertor.consumer.interfaces;

import com.dataconvertor.consumer.dao.OperationDao;

import java.io.IOException;

// strategy pattern
public interface DataWriter {
    void write(OperationDao operationResult) throws IOException;
}
