package com.rabbitmq.paulloazevedo.orderms.producer;

import com.rabbitmq.paulloazevedo.orderms.controller.dto.OrderResponse;
import com.rabbitmq.paulloazevedo.orderms.listener.OrderCreatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.rabbitmq.paulloazevedo.orderms.config.RabbitMqConfig.ORDER_COMPLETED_QUEUE;

@Component
public class OrderProcessedProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(OrderProcessedProducer.class);

    public OrderProcessedProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Page<OrderResponse> ordersReponse){
        logger.info("Message published: {}", ordersReponse);
        rabbitTemplate.convertAndSend(ORDER_COMPLETED_QUEUE, ordersReponse);
    }
}
