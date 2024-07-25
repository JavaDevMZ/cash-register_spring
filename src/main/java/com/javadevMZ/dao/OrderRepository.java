package com.javadevMZ.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

        List<Order> findAllByClosedAtBetween(LocalDateTime start, LocalDateTime end);
}
