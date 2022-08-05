package com.dataconvertor.consumer.interfaces;

import com.dataconvertor.consumer.dao.OperationDao;

// strategy pattern
public interface DataWriter {
    void write(OperationDao operationResult);
}
