package com.dataconvertor.consumer.impl;

import com.dataconvertor.consumer.dao.OperationDao;
import com.dataconvertor.consumer.interfaces.DataWriter;
import com.dataconvertor.consumer.repository.OperationRepository;
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
