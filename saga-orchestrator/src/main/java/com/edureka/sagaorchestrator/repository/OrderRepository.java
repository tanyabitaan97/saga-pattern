package com.edureka.sagaorchestrator.repository;

import com.edureka.sagaorchestrator.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
