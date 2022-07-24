package com.dataconvertor.producer.repository;

import com.dataconvertor.producer.dao.MessageDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDao, Integer> {
}
