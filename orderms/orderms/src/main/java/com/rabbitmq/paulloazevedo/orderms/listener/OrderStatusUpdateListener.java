package com.rabbitmq.paulloazevedo.orderms.listener;

import com.rabbitmq.paulloazevedo.orderms.DTO.OrderCreatedEvent;
import com.rabbitmq.paulloazevedo.orderms.DTO.OrderStatusEvent;
import com.rabbitmq.paulloazevedo.orderms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.rabbitmq.paulloazevedo.orderms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;
import static com.rabbitmq.paulloazevedo.orderms.config.RabbitMqConfig.ORDER_STATUS_UPDATE_QUEUE;

@Component
public class OrderStatusUpdateListener {

    private final Logger logger = LoggerFactory.getLogger(OrderStatusUpdateListener.class);

    private final OrderService orderService;

    public OrderStatusUpdateListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_STATUS_UPDATE_QUEUE)
    public void listen(Message<OrderStatusEvent> message){
        logger.info("Message consumed Status Update: {}", message);
        orderService.updateStatus(message.getPayload());
    }


}
