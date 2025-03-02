package ru.practicum.store.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.store.dto.CreateCartDto;
import ru.practicum.store.dto.GetCartDto;
import ru.practicum.store.model.Cart;

@Mapper(componentModel = "spring")
public interface CartConverter {

    @Mapping(target = "productTitle", expression = "java(cart.getProduct().getTitle())")
    @Mapping(target = "productPrice", expression = "java(cart.getProduct().getPrice())")
    @Mapping(target = "productImage", expression = "java(cart.getProduct().getImage())")
    @Mapping(target = "productDescription", expression = "java(cart.getProduct().getDescription())")
    GetCartDto toDto(Cart cart);

    @Mapping(source = "productId", target = "product", ignore = true)
    Cart toCart(CreateCartDto dto);
}
