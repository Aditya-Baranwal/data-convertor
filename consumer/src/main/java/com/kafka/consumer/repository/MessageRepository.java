package com.kafka.consumer.repository;

import com.kafka.consumer.dao.MessageDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDao, Integer> {
}
