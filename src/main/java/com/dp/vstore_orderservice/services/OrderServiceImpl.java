package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.dtos.OrderItemDto;
import com.dp.vstore_orderservice.exceptions.OrderCannotBePlacedException;
import com.dp.vstore_orderservice.models.Order;
import com.dp.vstore_orderservice.models.Payment;
import com.dp.vstore_orderservice.repositories.OrderRepository;
import com.dp.vstore_orderservice.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartClient cartClient, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.cartClient = cartClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public OrderDto createOrder(Long userId) throws Exception {
        OrderItemDto cart = cartClient.getCart(userId);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new OrderCannotBePlacedException("Your cart is empty, please select an item.");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setCartId(cart.getCartId());
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order.setItems(new ArrayList<>());
        setOrderItems(cart.getItems(), order);

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(calculatePrice(cart.getItems()));
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);

        order.setPayments(new ArrayList<>());
        order.getPayments().add(payment);

        orderRepository.save(order);

        return OrderDto.from(order, payment);
    }

    @Override
    public List<OrderDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserIdAndDeletedFalse(userId);
        return orders.stream().map(order -> {
            List<Payment> payments = paymentRepository.findByOrderId(order.getId());
            return OrderDto.from(order, payments.getLast());
        }).toList();
    }

    private double calculatePrice(Map<OrderItemDto.ItemsDto, Integer> items) {
        double price = 0.0;
        for(Map.Entry<OrderItemDto.ItemsDto, Integer> entry : items.entrySet()) {
            OrderItemDto.ItemsDto item = entry.getKey();
            Integer quantity = entry.getValue();

            price += item.getProductPrice()*quantity;
        }
        return price;
    }

    private void setOrderItems(Map<OrderItemDto.ItemsDto, Integer> items, Order order) {
        for(Map.Entry<OrderItemDto.ItemsDto, Integer> entry : items.entrySet()) {
            OrderItemDto.ItemsDto item = entry.getKey();
            Integer quantity = entry.getValue();
            Order.Items itemEntity = new Order.Items();
            itemEntity.setProductId(item.getProductId());
            itemEntity.setQuantity(quantity);

            order.getItems().add(itemEntity);
        }
    }
}
