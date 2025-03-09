package ru.practicum.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.store.model.ProductOrder;
import ru.practicum.store.model.ProductOrderId;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderId> {
}
