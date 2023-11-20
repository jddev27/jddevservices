package com.jddev;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, Integer> {

}
