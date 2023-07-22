package com.dataconvertor.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    private static final String TOPIC_1 = "operation-topic";

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(TOPIC_1).build();
    }

}
