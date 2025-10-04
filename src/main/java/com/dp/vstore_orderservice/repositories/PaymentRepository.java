package com.dp.vstore_orderservice.repositories;

import com.dp.vstore_orderservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByOrderId(Long orderId);
}
