package com.dp.vstore_orderservice.dtos;

import com.dp.vstore_orderservice.models.Order;
import com.dp.vstore_orderservice.models.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class OrderDto {
    private String orderId;
    private String customerId;
    private Map<Long, Integer> items;
    private String orderStatus;
    private Double totalPrice;

    public static OrderDto from(Order order, Payment payment) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId().toString());
        orderDto.setCustomerId(order.getUserId().toString());
        orderDto.setItems(new HashMap<>());
        order.getItems().forEach(item -> {
            orderDto.getItems().put(item.getProductId(), item.getQuantity());
        });
        orderDto.setOrderStatus(order.getOrderStatus().toString());
        orderDto.setTotalPrice(payment.getAmount());
        return orderDto;
    }
}
