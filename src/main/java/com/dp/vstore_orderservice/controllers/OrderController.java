package com.dp.vstore_orderservice.controllers;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.security.UserPrincipal;
import com.dp.vstore_orderservice.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public  OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private Long getUserIdFromContext() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(principal.id());
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder() throws Exception {
        Long userId = getUserIdFromContext();
        return new  ResponseEntity<>(orderService.createOrder(userId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrder() {
        Long userId = getUserIdFromContext();
        return new ResponseEntity<>(orderService.getOrders(userId), HttpStatus.OK);
    }
}
