package ru.practicum.store.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.store.converter.ProductConverter;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.dto.GetProductDto;
import ru.practicum.store.exception.EntityNotFoundException;
import ru.practicum.store.model.Product;
import ru.practicum.store.repository.ProductRepository;
import ru.practicum.store.service.impl.ProductServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        product = new Product(1L, "Product 1", "image1.jpg", 10000, "Description", null, null);  // Price stored as long
    }

    @Test
    void testCreateProduct() {
        CreateProductDto createProductDto = new CreateProductDto("Product 1", "image1.jpg", 100.0, "Description");

        // Mock dependencies
        when(productConverter.convertToProduct(createProductDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productConverter.convertToGetProductDto(product)).thenReturn(new GetProductDto(1L, "Product 1", "image1.jpg", 100.0, "Description", null));

        // Call method
        GetProductDto result = productService.create(createProductDto);

        // Assert results
        assertNotNull(result);
        assertEquals("Product 1", result.getTitle());
        assertEquals(100.0, result.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testFindProductById() {
        long productId = 1L;

        // Mock dependencies
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productConverter.convertToGetProductDto(product)).thenReturn(new GetProductDto(1L, "Product 1", "image1.jpg", 100.0, "Description", null));

        // Call method
        Product result = productService.findById(productId);

        // Assert results
        assertNotNull(result);
        assertEquals("Product 1", result.getTitle());
        assertEquals(10000, result.getPrice());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindProductById_NotFound() {
        long productId = 999L;

        // Mock dependencies
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Call method and assert exception
        assertThrows(EntityNotFoundException.class, () -> productService.findById(productId));
    }

    @Test
    void testUpdateProduct() {
        long productId = 1L;
        CreateProductDto updateDto = new CreateProductDto("Updated Product", "image1_updated.jpg", 150.0, "Updated description");

        // Mock dependencies
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productConverter.convertToProduct(updateDto)).thenReturn(new Product(productId, "Updated Product", "image1_updated.jpg", 15000, "Updated description", null, null));

        // Call method
        productService.update(updateDto, productId);

        // Verify interactions
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        long productId = 1L;

        // Call method
        productService.delete(productId);

        // Verify interactions
        verify(productRepository, times(1)).deleteById(productId);
    }

}