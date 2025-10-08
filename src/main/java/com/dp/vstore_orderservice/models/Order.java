package com.dp.vstore_orderservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "orders")
@Getter @Setter
public class Order extends BaseModel {
    private Long userId;
    private Long cartId;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Item> items;
    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Payment> payments;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        FAILED,
    }
}
