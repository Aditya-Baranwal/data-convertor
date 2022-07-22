package com.kafka.producer.repository;

import com.kafka.producer.dao.MessageDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDao, Integer> {
}
