package com.dataconvertor.consumer.repository;

import com.dataconvertor.consumer.dao.OperationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<OperationDao, Integer> {
}
