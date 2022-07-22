package com.kafka.consumer.repository;

import com.kafka.consumer.dao.OperationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<OperationDao, Integer> {
}
