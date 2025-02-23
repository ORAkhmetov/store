package ru.practicum.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;

public interface ProductService {

    GetProductDto create(CreateProductDto dto);

    Page<GetProductDto> findAll(Pageable pageable);

    GetProductDto findById(Long id);

    void update(CreateProductDto dto, Long id);

    void delete(Long id);
}
