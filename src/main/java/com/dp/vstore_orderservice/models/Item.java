package com.dp.vstore_orderservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "items")
public class Item extends BaseModel{
    private Long productId;
    private int quantity;
    private double price;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Order order;
}