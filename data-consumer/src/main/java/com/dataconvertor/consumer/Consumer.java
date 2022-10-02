package com.dataconvertor.consumer;

import com.dataconvertor.consumer.repository.MessageRepository;
import com.dataconvertor.consumer.repository.OperationRepository;
import com.google.gson.Gson;
import com.dataconvertor.consumer.dao.MessageDao;
import com.dataconvertor.consumer.dao.OperationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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

        // process
        OperationDao operationDetail = new OperationDao(
                receviedMessage.getNumber1(),
                receviedMessage.getNumber2(),
                receviedMessage.getOperation(),
                receviedMessage.getMessage_id(),
                appConfiguration.getOperationProcessorConfig().get(receviedMessage.getOperation()).process(receviedMessage.getNumber1(), receviedMessage.getNumber2())
        );

        // write details
        appConfiguration.getDestinationWriterConfig().get(receviedMessage.getDestination()).write(operationDetail);

        messageRepository.save(receviedMessage);
        operationRepository.save(operationDetail);
    }
}