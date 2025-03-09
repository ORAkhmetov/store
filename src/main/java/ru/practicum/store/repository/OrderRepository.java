package ru.practicum.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.store.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
