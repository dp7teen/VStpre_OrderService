package com.dp.vstore_orderservice.controllers;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.exceptions.IncorrectAmountException;
import com.dp.vstore_orderservice.exceptions.OrderNotPresentException;
import com.dp.vstore_orderservice.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}/{amount}")
    public ResponseEntity<OrderDto> pay(@PathVariable Long orderId,
                                        @PathVariable double amount) throws Exception {
        return new ResponseEntity<>(paymentService.pay(orderId, amount), HttpStatus.OK);
    }
}
