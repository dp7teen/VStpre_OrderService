package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderDto;
import com.dp.vstore_orderservice.dtos.OrderItemDto;
import com.dp.vstore_orderservice.exceptions.OrderCannotBePlacedException;
import com.dp.vstore_orderservice.models.Item;
import com.dp.vstore_orderservice.models.Order;
import com.dp.vstore_orderservice.models.Payment;
import com.dp.vstore_orderservice.repositories.ItemRepository;
import com.dp.vstore_orderservice.repositories.OrderRepository;
import com.dp.vstore_orderservice.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartClient cartClient,
                            PaymentRepository paymentRepository,
                            ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.cartClient = cartClient;
        this.paymentRepository = paymentRepository;
        this.itemRepository = itemRepository;
    }


    @Override
    public OrderDto createOrder(Long userId) throws Exception {
        OrderItemDto cart = cartClient.getCart(userId);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new OrderCannotBePlacedException("Your cart is empty, please select an item.");
        }
        List<Order> orders = orderRepository.findAllByUserIdAndDeletedFalse(userId);

        if (!orders.isEmpty() && orders.getLast().getOrderStatus() == Order.OrderStatus.PENDING) {
            orders.getLast().getItems().clear();
            setOrderItems(cart.getItems(), orders.getLast());
            orderRepository.save(orders.getLast());
            Payment payment = setPayment(cart.getItems(), orders.getLast().getPayments().getLast());
            return OrderDto.from(orders.getLast(), payment);
        }

        Order order = setOrder(userId, cart);
        Payment payment = setPayment(cart.getItems(), order);

        return OrderDto.from(order, payment);
    }

    @Override
    public List<OrderDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserIdAndDeletedFalse(userId);
        return orders.stream().map(order -> {
            List<Payment> payments = paymentRepository.findByOrderIdAndDeletedFalse(order.getId());
            return OrderDto.from(order, payments.getLast());
        }).toList();
    }

    private Order setOrder(Long userId, OrderItemDto cart) {
        Order order = new Order();
        order.setUserId(userId);
        order.setCartId(cart.getCartId());
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order.setItems(new ArrayList<>());
        setOrderItems(cart.getItems(), order);
        orderRepository.save(order);
        return order;
    }

    private Payment setPayment(List<OrderItemDto.ProductDto> items, Order order) {
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(calculatePrice(items));
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        paymentRepository.save(payment);

        order.setPayments(new ArrayList<>());
        order.getPayments().add(payment);
        orderRepository.save(order);
        return payment;
    }

    private Payment setPayment(List<OrderItemDto.ProductDto> items, Payment payment) {
        double amount = calculatePrice(items);
        payment.setAmount(amount);
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        paymentRepository.save(payment);
        return payment;
    }

    private double calculatePrice(List<OrderItemDto.ProductDto> items) {
        double price = 0.0;
        for(OrderItemDto.ProductDto dto : items) {
            price += dto.getProduct().getProductPrice() * dto.getQuantity();
        }
        return Math.round(price * 100.0) / 100.0;
    }

    private void setOrderItems(List<OrderItemDto.ProductDto> items, Order order) {
        for(OrderItemDto.ProductDto dto : items) {

            Item item = new Item();
            item.setProductId(dto.getProduct().getProductId());
            item.setQuantity(dto.getQuantity());
            item.setPrice(dto.getProduct().getProductPrice());
            itemRepository.save(item);

            order.getItems().add(item);
            orderRepository.save(order);
        }
    }
}
