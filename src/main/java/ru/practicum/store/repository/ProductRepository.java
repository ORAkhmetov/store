package ru.practicum.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
