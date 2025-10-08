package com.dp.vstore_orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class OrderItemDto {
    private Long cartId;
    private List<ProductDto> items;

    @Getter @Setter
    public static class ItemsDto {
        private Long productId;
        private String productName;
        private Double productPrice;
        private Double rating;
    }

    @Getter @Setter
    public static class ProductDto {
        private ItemsDto product;
        private int quantity;
    }
}
