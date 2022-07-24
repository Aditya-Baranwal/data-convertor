package com.dataconvertor.consumer.impl;

import com.dataconvertor.consumer.interfaces.DataProcessor;
import org.springframework.stereotype.Component;

@Component
public class DivideProcess implements DataProcessor {

    @Override
    public int process(int num1, int num2) {
        return num1/num2;
    }

}
