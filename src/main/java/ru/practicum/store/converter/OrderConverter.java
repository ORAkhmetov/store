package ru.practicum.store.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.store.dto.GetOrderDtoInAll;
import ru.practicum.store.model.Order;
import ru.practicum.store.model.ProductOrder;

@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mapping(target = "totalPrice", expression = "java(mapAndSumPrice(order))")
    @Mapping(target = "products", source = "productOrders")
    GetOrderDtoInAll toGetOrderDtoInAll(Order order);

    default double mapAndSumPrice(Order order) {
        return order.getProductOrders().stream()
                .mapToDouble(productOrder -> productOrder.getPrice() * productOrder.getQuantity() / 100.0)
                .sum();
    }

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "title", source = "product.title")
    @Mapping(target = "image", source = "product.image")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", expression = "java(productOrder.getPrice() / 100.0)")
    GetOrderDtoInAll.ProductInOrderDto toProductInOrderDto(ProductOrder productOrder);

    default List<GetOrderDtoInAll.ProductInOrderDto> mapProductOrders(List<ProductOrder> productOrders) {
        return productOrders.stream()
                .map(this::toProductInOrderDto)
                .collect(Collectors.toList());
    }
}
