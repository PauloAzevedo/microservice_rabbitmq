package com.rabbitmq.paulloazevedo.orderms.repository;

import com.rabbitmq.paulloazevedo.orderms.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {


    Page<OrderEntity> findAllByCustomerId(long customerId, PageRequest pageRequest);

    Optional<OrderEntity> findByOrderId(long orderId);
}

