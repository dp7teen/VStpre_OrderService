package com.dp.vstore_orderservice.repositories;

import com.dp.vstore_orderservice.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
