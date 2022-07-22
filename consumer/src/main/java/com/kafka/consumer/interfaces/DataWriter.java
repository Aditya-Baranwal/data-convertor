package com.kafka.consumer.interfaces;

import com.kafka.consumer.dao.OperationDao;

import java.io.IOException;

// strategy pattern
public interface DataWriter {
    void write(OperationDao operationResult);
}
