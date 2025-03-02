package ru.practicum.store.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.store.converter.CartConverter;
import ru.practicum.store.dto.CreateCartDto;
import ru.practicum.store.dto.GetCartDto;
import ru.practicum.store.model.Cart;
import ru.practicum.store.model.Product;
import ru.practicum.store.repository.CartRepository;
import ru.practicum.store.service.CartService;
import ru.practicum.store.service.ProductService;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartConverter converter;

    private final ProductService productService;

    @Override
    public void createCart(CreateCartDto createCartDto) {
        Cart cart = converter.toCart(createCartDto);
        Product product = productService.findById(createCartDto.getProductId());
        cart.setProduct(product);
        cartRepository.save(cart);
    }

    @Override
    public List<GetCartDto> findAllCarts() {
        return cartRepository.findAll().stream().map(converter::toDto).collect(Collectors.toList());
    }

    @Override
    public void increaseQuantity(long id) {
        cartRepository.increaseQuantity(id);
    }

    @Override
    @Transactional
    public void decreaseQuantity(long id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            if (cartOptional.get().getQuantity() == 1) {
                cartRepository.deleteById(id);
            } else {
                cartRepository.decreaseQuantity(id);
            }
        }
    }

    @Override
    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }
}
