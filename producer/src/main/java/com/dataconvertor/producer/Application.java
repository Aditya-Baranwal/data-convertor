package com.dataconvertor.producer;

import com.dataconvertor.producer.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dataconvertor.producer.dao.MessageDao;
import com.dataconvertor.common.enums.Destination;
import com.dataconvertor.common.enums.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class Application {

	@Autowired
	private ApplicationContext context;

	@Autowired
    MessageRepository messageRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// producing message at rate of 500 message per second.
	@EventListener(ApplicationReadyEvent.class)
	public void pushEventsToKafka() throws InterruptedException, JsonProcessingException {
		 Producer producer = context.getBean(Producer.class);
		while (true) {
			MessageDao message = new MessageDao(Operation.values()[this.generateRandomNumber(0,3)], this.generateRandomNumber(0, 100), this.generateRandomNumber(101, 200), Destination.values()[this.generateRandomNumber(0,2)]);
			ObjectMapper objectMapper = new ObjectMapper();
			producer.sendMessage(objectMapper.writeValueAsString(message));
			messageRepository.save(message);
			sleep(10);
		}
	}

	public int generateRandomNumber(int min, int max) {
		return (int)Math.floor(Math.random() * (max - min + 1) + min);
	}
}
