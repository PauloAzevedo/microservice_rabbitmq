package com.rabbitmq.paulloazevedo.orderms.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String ORDER_CREATED_QUEUE = "service-order-created";
    public static final String ORDER_COMPLETED_QUEUE = "service-order-completed";
    public static final String ORDER_STATUS_UPDATE_QUEUE = "service-order-status-update";
    public static final String ORDER_STATUS_NOTIFICATION_QUEUE = "service-order-notification";


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarable orderCreatedQueue(){
        return new Queue(ORDER_CREATED_QUEUE);
    }

    @Bean
    public Declarable orderProcessedQueue(){
        return new Queue(ORDER_COMPLETED_QUEUE);
    }

    @Bean
    public Declarable orderStatusUpdateQueue(){
        return new Queue(ORDER_STATUS_UPDATE_QUEUE);
    }

    @Bean
    public Declarable orderNotificationQueue(){
        return new Queue(ORDER_STATUS_NOTIFICATION_QUEUE);
    }
}
