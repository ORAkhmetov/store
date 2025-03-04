package ru.practicum.store.service;

import java.util.List;

import ru.practicum.store.dto.CreateCartDto;
import ru.practicum.store.dto.GetCartDto;
import ru.practicum.store.model.Cart;

public interface CartService {

    void createCart(CreateCartDto createCartDto);

    List<GetCartDto> findAllCarts();

    void increaseQuantity(long id);

    void decreaseQuantity(long id);

    void deleteCart(long id);

    Cart findCartByProductId(long id);
}
