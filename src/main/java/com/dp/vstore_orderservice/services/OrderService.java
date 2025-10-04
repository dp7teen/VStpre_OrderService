package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDto createOrder(Long userId) throws Exception;

    List<OrderDto> getOrders(Long userId);
}
