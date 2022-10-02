package com.dataconvertor.consumer.impl.processor;

import com.dataconvertor.consumer.interfaces.DataProcessor;
import org.springframework.stereotype.Component;

@Component
public class AddProcess implements DataProcessor {

    public int process(int num1, int num2) {
        return num1 + num2;
    }
}
