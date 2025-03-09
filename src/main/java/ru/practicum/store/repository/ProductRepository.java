package ru.practicum.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Product> findByIdIn(List<Long> ids);
}
