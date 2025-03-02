package ru.practicum.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.store.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cart SET quantity = quantity + 1 WHERE id = ?1")
    void increaseQuantity(Long id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cart SET quantity = quantity - 1 WHERE id = ?1")
    void decreaseQuantity(Long id);
}
