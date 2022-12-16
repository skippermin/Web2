package com.example.repository;

import com.example.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
// 즉시 로딩
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
