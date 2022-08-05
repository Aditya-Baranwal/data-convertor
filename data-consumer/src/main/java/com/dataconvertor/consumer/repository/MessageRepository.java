package com.dataconvertor.consumer.repository;

import com.dataconvertor.consumer.dao.MessageDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDao, Integer> {
}
