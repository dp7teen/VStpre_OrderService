package com.dp.vstore_orderservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
public class Payment extends BaseModel {
    private Long orderId;
    private Double amount;
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        PENDING,
        PAID,
    }
}
