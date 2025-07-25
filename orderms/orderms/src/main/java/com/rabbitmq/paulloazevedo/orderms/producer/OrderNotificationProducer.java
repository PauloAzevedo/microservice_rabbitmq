package com.rabbitmq.paulloazevedo.orderms.producer;

import com.rabbitmq.paulloazevedo.orderms.controller.dto.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.rabbitmq.paulloazevedo.orderms.config.RabbitMqConfig.*;

@Component
public class OrderNotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(OrderNotificationProducer.class);

    public OrderNotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(OrderResponse ordersReponse){
        logger.info("Message published Status Update: {}", ordersReponse);
        rabbitTemplate.convertAndSend(ORDER_STATUS_NOTIFICATION_QUEUE, ordersReponse);
    }


}
