package com.rabbitmq.paulloazevedo.orderms.controller;

import com.rabbitmq.paulloazevedo.orderms.controller.dto.ApiResponse;
import com.rabbitmq.paulloazevedo.orderms.controller.dto.OrderResponse;
import com.rabbitmq.paulloazevedo.orderms.controller.dto.PaginationResponse;
import com.rabbitmq.paulloazevedo.orderms.entity.StatusOrderEnum;
import com.rabbitmq.paulloazevedo.orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
            @PathVariable("customerId") Long customerId,
            @RequestParam(name="page", defaultValue = "0") Integer page,
            @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize
    ){
        var body = orderService.finalAllByCustomerId(customerId, PageRequest.of(page,pageSize));
        var totalOrders = orderService.findTotalOnOrdersByCustomerId(customerId);
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOrders", totalOrders),
                body.getContent(),
                PaginationResponse.fromPage(body)
        ) );
    }

}
