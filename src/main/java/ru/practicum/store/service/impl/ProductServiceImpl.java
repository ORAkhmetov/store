package ru.practicum.store.service.impl;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.store.converter.CartConverter;
import ru.practicum.store.converter.ProductConverter;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.exception.EntityNotFoundException;
import ru.practicum.store.model.Cart;
import ru.practicum.store.model.Product;
import ru.practicum.store.model.SortType;
import ru.practicum.store.repository.CartRepository;
import ru.practicum.store.repository.ProductRepository;
import ru.practicum.store.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    private final CartRepository cartRepository;

    private final CartConverter cartConverter;

    @Override
    public GetProductDto create(CreateProductDto dto) {
        Product product = productConverter.convertToProduct(dto);
        Product saved = productRepository.save(product);
        return productConverter.convertToGetProductDto(saved);
    }

    @Override
    public Page<GetProductDto> findAll(int page, int size, SortType sortType, String searchString) {
        if (searchString != null) {
            return productRepository.findAllByTitleContainingIgnoreCase(searchString, getPageable(page, size, sortType))
                    .map(productConverter::convertToGetProductDto)
                    .map(this::setCartInProduct);
        } else {
            return productRepository.findAll(getPageable(page, size, sortType))
                    .map(productConverter::convertToGetProductDto)
                    .map(this::setCartInProduct);
        }
    }

    private GetProductDto setCartInProduct(GetProductDto dto) {
        Optional<Cart> cart = cartRepository.findCartByProductId(dto.getId());
        dto.setCart(cart.map(cartConverter::cartToDto).orElse(null));
        return dto;
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public GetProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productConverter::convertToGetProductDto)
                .map(this::setCartInProduct)
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

    private Pageable getPageable(int page, int size, SortType sortType) {
        String sortedBy = null;
        switch (sortType) {
            case ALPHA -> sortedBy = "title";
            case PRICE -> sortedBy = "price";
        }
        Sort sort = sortedBy != null ? Sort.by(Sort.Order.asc(sortedBy)) : Sort.unsorted();
        return PageRequest.of(page, size, sort);
    }
}
