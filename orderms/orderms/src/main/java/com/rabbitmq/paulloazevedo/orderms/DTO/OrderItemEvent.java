package com.rabbitmq.paulloazevedo.orderms.DTO;

import java.math.BigDecimal;

public record OrderItemEvent(String produto,
                             Integer quantidade,
                             BigDecimal preco) {
}
