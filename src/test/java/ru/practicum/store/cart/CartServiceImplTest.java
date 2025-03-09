package ru.practicum.store.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.practicum.store.converter.CartConverter;
import ru.practicum.store.dto.CreateCartDto;
import ru.practicum.store.dto.GetCartDto;
import ru.practicum.store.exception.EntityNotFoundException;
import ru.practicum.store.model.Cart;
import ru.practicum.store.model.Product;
import ru.practicum.store.repository.CartRepository;
import ru.practicum.store.service.ProductService;
import ru.practicum.store.service.impl.CartServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartConverter cartConverter;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        product = new Product(1L, "Product 1", "image1.jpg", 100, "Description", null, null);
        cart = new Cart(1L, product, 2);
    }

    @Test
    void testCreateCart() {
        CreateCartDto createCartDto = new CreateCartDto(1L);

        // Mock dependencies
        when(productService.findById(1L)).thenReturn(product);
        when(cartConverter.toCart(createCartDto)).thenReturn(cart);

        // Call method
        cartService.createCart(createCartDto);

        // Verify interactions
        verify(productService, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testFindAllCarts() {
        List<Cart> carts = Arrays.asList(cart);
        List<GetCartDto> expectedDtos = Arrays.asList(new GetCartDto(1L, 1L, "Product 1", "image1.jpg", "Description", 100, 2));

        // Mock dependencies
        when(cartRepository.findAll()).thenReturn(carts);
        when(cartConverter.toDto(cart)).thenReturn(expectedDtos.get(0));

        // Call method
        List<GetCartDto> result = cartService.findAllCarts();

        // Assert results
        assertEquals(1, result.size());
        assertEquals("Product 1", result.get(0).getProductTitle());
        verify(cartRepository, times(1)).findAll();
    }

    @Test
    void testIncreaseQuantity() {
        long cartId = 1L;

        // Call method
        cartService.increaseQuantity(cartId);

        // Verify interactions
        verify(cartRepository, times(1)).increaseQuantity(cartId);
    }

    @Test
    void testDecreaseQuantity() {
        long cartId = 1L;

        // Mock dependencies
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // Call method
        cartService.decreaseQuantity(cartId);

        // Verify interactions
        verify(cartRepository, times(1)).decreaseQuantity(cartId);
    }

    @Test
    void testDecreaseQuantity_DeleteCart() {
        long cartId = 1L;

        // Mock dependencies
        cart.setQuantity(1);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // Call method
        cartService.decreaseQuantity(cartId);

        // Verify interactions
        verify(cartRepository, times(1)).deleteById(cartId);
    }

    @Test
    void testDeleteCart() {
        long cartId = 1L;

        // Call method
        cartService.deleteCart(cartId);

        // Verify interactions
        verify(cartRepository, times(1)).deleteById(cartId);
    }

    @Test
    void testFindCartByProductId() {
        long productId = 1L;

        // Mock dependencies
        when(cartRepository.findCartByProductId(productId)).thenReturn(Optional.of(cart));

        // Call method
        Cart result = cartService.findCartByProductId(productId);

        // Assert results
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindCartByProductId_NotFound() {
        long productId = 999L;

        // Mock dependencies
        when(cartRepository.findCartByProductId(productId)).thenReturn(Optional.empty());

        // Call method and assert exception
        assertThrows(EntityNotFoundException.class, () -> cartService.findCartByProductId(productId));
    }

    @Test
    void testCalculateSumPrice() {
        List<GetCartDto> carts = Arrays.asList(new GetCartDto(1L, 1L, "Product 1", "image1.jpg", "Description", 100, 2));

        // Call method
        double result = cartService.calculateSumPrice(carts);

        // Assert results
        assertEquals(200.0, result);
    }

    @Test
    void testClearCarts() {
        // Call method
        cartService.clearCarts();

        // Verify interactions
        verify(cartRepository, times(1)).deleteAll();
    }
}
