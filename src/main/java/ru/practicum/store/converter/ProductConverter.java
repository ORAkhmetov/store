package ru.practicum.store.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.model.Product;

@Mapper(componentModel = "spring")
public interface ProductConverter {

    @Mapping(target = "price", qualifiedByName = "convertPriceToLong")
    Product convertToProduct(CreateProductDto dto);

    @Mapping(target = "price", qualifiedByName = "convertPriceToDouble")
    @Mapping(target = "cart", ignore = true)
    GetProductDto convertToGetProductDto(Product product);

    @Named("convertPriceToLong")
    default long convertPriceToLong(double price) {
        return (long) (price * 100);
    }

    @Named("convertPriceToDouble")
    default double convertPriceToDouble(long price) {
        return (double) price / 100;
    }
}
