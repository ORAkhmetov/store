package ru.practicum.store.service.impl;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.store.converter.ProductConverter;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.exception.EntityNotFoundException;
import ru.practicum.store.model.Product;
import ru.practicum.store.repository.ProductRepository;
import ru.practicum.store.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    @Override
    public GetProductDto create(CreateProductDto dto) {
        Product product = productConverter.convertToProduct(dto);
        Product saved = productRepository.save(product);
        return productConverter.convertToGetProductDto(saved);
    }

    @Override
    public Page<GetProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productConverter::convertToGetProductDto);
    }

    @Override
    public GetProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productConverter::convertToGetProductDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public void update(CreateProductDto dto, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productConverter.convertToProduct(dto);
            product.setId(id);
            product.setCreatedAt(productOptional.get().getCreatedAt());
            productRepository.save(product);
        }
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
