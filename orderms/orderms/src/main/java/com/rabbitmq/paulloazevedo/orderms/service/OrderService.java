package com.rabbitmq.paulloazevedo.orderms.service;

import com.rabbitmq.paulloazevedo.orderms.DTO.OrderCreatedEvent;
import com.rabbitmq.paulloazevedo.orderms.DTO.OrderStatusEvent;
import com.rabbitmq.paulloazevedo.orderms.controller.dto.OrderResponse;
import com.rabbitmq.paulloazevedo.orderms.entity.OrderEntity;
import com.rabbitmq.paulloazevedo.orderms.entity.OrderItem;
import com.rabbitmq.paulloazevedo.orderms.producer.OrderNotificationProducer;
import com.rabbitmq.paulloazevedo.orderms.producer.OrderProcessedProducer;
import com.rabbitmq.paulloazevedo.orderms.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;
    private final OrderProcessedProducer orderProcessedProducer;
    private final OrderNotificationProducer orderNotificationProducer;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate, OrderProcessedProducer orderProcessedProducer, OrderNotificationProducer orderNotificationProducer) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
        this.orderProcessedProducer = orderProcessedProducer;
        this.orderNotificationProducer = orderNotificationProducer;
    }

    public void save(OrderCreatedEvent event){
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(
                getCollectItems(event)
        );
        entity.setTotal(getTotal(event));
        orderRepository.save(entity);
    }

    public void updateStatus(OrderStatusEvent event) {
        var orderOpt = orderRepository.findByOrderId(event.codigoPedido());
        if(orderOpt.isPresent()){
            OrderEntity order = orderOpt.get();
            order.setStatus(event.status());
            orderRepository.save(order);
            var ordersReponse = OrderResponse.fromEntity(order);
            orderNotificationProducer.publish(ordersReponse);
        }
    }

    public Page<OrderResponse> finalAllByCustomerId(long customerId, PageRequest pageRequest){

        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        var ordersReponse = orders.map(OrderResponse::fromEntity);
        orderProcessedProducer.publish(ordersReponse);
        ordersReponse.stream().forEach(
                orderNotificationProducer::publish
        );
        return ordersReponse;
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream().map(
                item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade()))
                )
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    private static List<OrderItem> getCollectItems(OrderCreatedEvent event) {
        return event.itens().stream().map(
                item -> new OrderItem(item.produto(), item.quantidade(), item.preco())
        ).collect(Collectors.toList());
    }
}
