package com.dataconvertor.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {
    @Value("${kafka-topic.topic-1.name}")
    String TOPIC_1_NAME;
    @Value("${kafka-topic.topic-1.partition}")
    Integer TOPIC_1_PARTITION;
    @Value("${kafka-topic.topic-1.replication-factor}")
    Integer TOPIC_1_REPLICATION_FACTOR;

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(TOPIC_1_NAME).partitions(TOPIC_1_PARTITION).build();
    }

}