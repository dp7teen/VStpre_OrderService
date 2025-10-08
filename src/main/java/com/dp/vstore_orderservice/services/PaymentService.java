package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.exceptions.IncorrectAmountException;
import com.dp.vstore_orderservice.exceptions.OrderNotPresentException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PaymentService {
    OrderDto pay(Long orderId, double amount) throws Exception;
}
