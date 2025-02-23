package ru.practicum.store.converter;

import org.mapstruct.Mapper;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.model.Product;

@Mapper(componentModel = "spring")
public interface ProductConverter {

    Product convertToProduct(CreateProductDto createProductDto);

    GetProductDto convertToGetProductDto(Product product);
}
