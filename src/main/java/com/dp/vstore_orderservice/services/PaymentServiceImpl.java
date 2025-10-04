package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.exceptions.IncorrectAmountException;
import com.dp.vstore_orderservice.exceptions.OrderNotPresentException;
import com.dp.vstore_orderservice.models.Order;
import com.dp.vstore_orderservice.models.Payment;
import com.dp.vstore_orderservice.repositories.OrderRepository;
import com.dp.vstore_orderservice.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,  OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto pay(Long orderId, Double amount) throws OrderNotPresentException, IncorrectAmountException {
        Optional<Order> optionalOrder = orderRepository.findByIdAndDeletedFalse(orderId);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotPresentException(String.format("The optionalOrder with id : %s is not found.", orderId));
        }
        Order order = optionalOrder.get();
        if (amount != 0 && amount < order.getPayments().getLast().getAmount()) {
            throw new IncorrectAmountException(String.format("The amount : %s is not enough.", amount));
        }
        else if (amount != 0 &&  amount > order.getPayments().getLast().getAmount()) {
            throw new IncorrectAmountException(String.format("The amount : %s is not correct.", amount));
        }
        else if (amount.equals(order.getPayments().getLast().getAmount())) {
            makePayment(order);
        }
        else {
            throw new IncorrectAmountException(String.format("The amount : %s is not correct.", amount));
        }
        return OrderDto.from(order, order.getPayments().getLast());
    }

    private void makePayment(Order order) {
        List<Payment> payments = paymentRepository.findByOrderId(order.getId());
        Payment payment = payments.getLast();
        payment.setPaymentStatus(Payment.PaymentStatus.PAID);
        paymentRepository.save(payment);
    }
}
