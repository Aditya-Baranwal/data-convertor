package com.kafka.consumer.impl;

import com.kafka.consumer.dao.OperationDao;
import com.kafka.consumer.interfaces.DataWriter;
import com.kafka.consumer.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBWriter implements DataWriter {

    @Autowired
    OperationRepository operationRepository;

    @Override
    public void write(OperationDao operationResult) {
        operationRepository.save(operationResult);
    }
}
