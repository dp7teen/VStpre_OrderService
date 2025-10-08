package com.dp.vstore_orderservice.dtos;

import com.dp.vstore_orderservice.models.Order;
import com.dp.vstore_orderservice.models.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class OrderDto {
    private String orderId;
    private String customerId;
    private List<ItemDetailsDto> items;
    private String orderStatus;
    private String paymentStatus;
    private Double totalPrice;

    public static OrderDto from(Order order, Payment payment) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId().toString());
        orderDto.setCustomerId(order.getUserId().toString());
        orderDto.setItems(new ArrayList<>());
        order.getItems().forEach(item -> {
            ItemDetailsDto itemDetailsDto = new ItemDetailsDto();
            itemDetailsDto.setProductId(item.getProductId());
            itemDetailsDto.setQuantity(item.getQuantity());
            itemDetailsDto.setPrice(item.getPrice());
            orderDto.getItems().add(itemDetailsDto);
        });
        orderDto.setOrderStatus(order.getOrderStatus().toString());
        orderDto.setPaymentStatus(payment.getPaymentStatus().toString());
        orderDto.setTotalPrice(payment.getAmount());
        return orderDto;
    }
}
