package com.rabbitmq.paulloazevedo.orderms.DTO;

import java.util.List;

public record OrderCreatedEvent(Long codigoPedido,
                                Long codigoCliente,
                                List<OrderItemEvent> itens) {

}
