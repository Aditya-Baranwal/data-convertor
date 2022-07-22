package com.kafka.consumer;

import com.google.gson.Gson;
import com.kafka.consumer.dao.MessageDao;
import com.kafka.consumer.dao.OperationDao;
import com.kafka.consumer.repository.MessageRepository;
import com.kafka.consumer.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static final String TOPIC = "operation-topic";

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    ApplicationConfiguration appConfiguration;

    @KafkaListener(topics = TOPIC, groupId = "1001")
    public void consume(String message) {
        logger.info(String.format("$$$ -> Consumed Message -> %s",message));

        MessageDao receviedMessage = new Gson().fromJson(message, MessageDao.class);
        OperationDao operationDetail = null;

        switch(receviedMessage.getOperation()) {
            case ADD:
                operationDetail = new OperationDao(receviedMessage.getNumber1(), receviedMessage.getNumber2(), receviedMessage.getOperation(),receviedMessage.getNumber1()+receviedMessage.getNumber2());
            break;
            case SUB:
                operationDetail = new OperationDao(receviedMessage.getNumber1(), receviedMessage.getNumber2(), receviedMessage.getOperation(),receviedMessage.getNumber1()-receviedMessage.getNumber2());
            break;
            case MUL:
                operationDetail = new OperationDao(receviedMessage.getNumber1(), receviedMessage.getNumber2(), receviedMessage.getOperation(),receviedMessage.getNumber1()*receviedMessage.getNumber2());
                break;
            case DIV:
                operationDetail = new OperationDao(receviedMessage.getNumber1(), receviedMessage.getNumber2(), receviedMessage.getOperation(),receviedMessage.getNumber1()/receviedMessage.getNumber2());
                break;
            default:
                throw new IllegalStateException("Unexpected operation value: " + receviedMessage.getOperation());
        }

        appConfiguration.getDestinationWriterConfig().get(receviedMessage.getDestination()).write(operationDetail);

        messageRepository.save(receviedMessage);
        operationRepository.save(operationDetail);
    }
}