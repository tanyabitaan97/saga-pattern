package com.edureka.airlineservice.repository;

import com.edureka.airlineservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Order, Long> {
}
