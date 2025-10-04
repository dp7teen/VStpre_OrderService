package com.dp.vstore_orderservice.models;

import com.dp.vstore_orderservice.dtos.OrderItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import java.util.List;
import java.util.Map;

@Entity
@Getter @Setter
public class Order extends BaseModel {
    private Long userId;
    private Long cartId;
    private List<Items> items;
    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Payment> payments;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
    }

    @Getter @Setter
    public static class Items {
        private Long productId;
        private Integer quantity;
    }
}
