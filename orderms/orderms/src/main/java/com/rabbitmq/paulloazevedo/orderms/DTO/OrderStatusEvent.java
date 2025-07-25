package com.rabbitmq.paulloazevedo.orderms.DTO;

import com.rabbitmq.paulloazevedo.orderms.entity.StatusOrderEnum;

public record OrderStatusEvent(Long codigoPedido,
                               StatusOrderEnum status) {
}
