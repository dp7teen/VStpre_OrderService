package com.dp.vstore_orderservice.repositories;

import com.dp.vstore_orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdAndDeletedFalse(Long userId);

    Optional<Order> findByIdAndDeletedFalse(Long orderId);
}
