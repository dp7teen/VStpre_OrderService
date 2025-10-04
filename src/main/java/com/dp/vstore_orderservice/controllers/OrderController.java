package com.dp.vstore_orderservice.controllers;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public  OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId) throws Exception {
        return new  ResponseEntity<>(orderService.createOrder(userId), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>> getOrder(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.getOrders(userId), HttpStatus.OK);
    }
}
