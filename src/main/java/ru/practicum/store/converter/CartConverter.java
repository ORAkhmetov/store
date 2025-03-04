package ru.practicum.store.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.store.dto.CartInProductDto;
import ru.practicum.store.dto.CreateCartDto;
import ru.practicum.store.dto.GetCartDto;
import ru.practicum.store.model.Cart;

@Mapper(componentModel = "spring")
public interface CartConverter {

    @Mapping(target = "productTitle", expression = "java(cart.getProduct().getTitle())")
    @Mapping(target = "productPrice", expression = "java(convertPriceToDouble(cart.getProduct().getPrice()))")
    @Mapping(target = "productImage", expression = "java(cart.getProduct().getImage())")
    @Mapping(target = "productDescription", expression = "java(cart.getProduct().getDescription())")
    GetCartDto toDto(Cart cart);

    @Mapping(source = "productId", target = "product", ignore = true)
    @Mapping(target = "quantity", constant = "1")
    Cart toCart(CreateCartDto dto);

    CartInProductDto cartToDto(Cart cart);

    default double convertPriceToDouble(long price) {
        return (double) price / 100;
    }
}
