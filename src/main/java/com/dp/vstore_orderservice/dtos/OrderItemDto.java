package com.dp.vstore_orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class OrderItemDto {
    private Long userId;
    private Long cartId;
    private Map<ItemsDto, Integer> items;

    @Getter @Setter
    public class ItemsDto {
        private Long productId;
        private String productName;
        private Double productPrice;
        private Double rating;
    }
}
