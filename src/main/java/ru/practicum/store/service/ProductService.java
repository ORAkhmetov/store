package ru.practicum.store.service;

import org.springframework.data.domain.Page;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.model.Product;
import ru.practicum.store.model.SortType;

public interface ProductService {

    GetProductDto create(CreateProductDto dto);

    Page<GetProductDto> findAll(int page, int size, SortType sortType);

    Product findById(long id);

    GetProductDto findById(Long id);

    void update(CreateProductDto dto, Long id);

    void delete(Long id);
}
