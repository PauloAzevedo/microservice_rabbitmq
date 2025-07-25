package com.rabbitmq.paulloazevedo.orderms.controller.dto;

import com.rabbitmq.paulloazevedo.orderms.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(
        Long orderId,
        Long customerId,
        String status,
        BigDecimal total
) {

    public static OrderResponse fromEntity(OrderEntity orderEntity) {
        return new OrderResponse(orderEntity.getOrderId(), orderEntity.getCustomerId(),orderEntity.getStatus().toString(), orderEntity.getTotal());
    }
}
