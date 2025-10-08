package com.dp.vstore_orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDetailsDto {
    private Long productId;
    private int quantity;
    private double price;
}
