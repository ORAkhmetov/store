package ru.practicum.store.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.store.converter.OrderConverter;
import ru.practicum.store.dto.CreateOrderDto;
import ru.practicum.store.dto.GetOrderDtoInAll;
import ru.practicum.store.exception.EntityNotFoundException;
import ru.practicum.store.model.Order;
import ru.practicum.store.model.Product;
import ru.practicum.store.model.ProductOrder;
import ru.practicum.store.repository.OrderRepository;
import ru.practicum.store.repository.ProductRepository;
import ru.practicum.store.service.CartService;
import ru.practicum.store.service.impl.OrderServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private Product product;
    private ProductOrder productOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        product = new Product(1L, "Product 1", "image1.jpg", 10000, "Description", null, null);  // Price stored as long
        order = new Order();
        productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setOrder(order);
        productOrder.setPrice(product.getPrice());
        productOrder.setQuantity(2);
        order.setProductOrders(Arrays.asList(productOrder));
    }

    @Test
    void testFindAllOrders() {
        List<Order> orders = Arrays.asList(order);

        // Mock dependencies
        when(orderRepository.findAll()).thenReturn(orders);
        when(orderConverter.toGetOrderDtoInAll(order)).thenReturn(new GetOrderDtoInAll(1L, 400.0, Arrays.asList(new GetOrderDtoInAll.ProductInOrderDto(1L, "Product 1", "image1.jpg", "Description", 2, 200.0))));

        // Call method
        List<GetOrderDtoInAll> result = orderService.findAll();

        // Assert results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(400.0, result.get(0).getTotalPrice());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testFindOrderById() {
        long orderId = 1L;

        // Mock dependencies
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderConverter.toGetOrderDtoInAll(order)).thenReturn(new GetOrderDtoInAll(1L, 400.0, Arrays.asList(new GetOrderDtoInAll.ProductInOrderDto(1L, "Product 1", "image1.jpg", "Description", 2, 200.0))));

        // Call method
        GetOrderDtoInAll result = orderService.findById(orderId);

        // Assert results
        assertNotNull(result);
        assertEquals(400.0, result.getTotalPrice());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testFindOrderById_NotFound() {
        long orderId = 999L;

        // Mock dependencies
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Call method and assert exception
        assertThrows(EntityNotFoundException.class, () -> orderService.findById(orderId));
    }

    @Test
    void testSaveOrder() {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        CreateOrderDto.ProductInOrderDto productDto = new CreateOrderDto.ProductInOrderDto(1L, 2);
        createOrderDto.setProducts(Arrays.asList(productDto));

        // Mock dependencies
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Call method
        Order result = orderService.save(createOrderDto);

        // Assert results
        assertNotNull(result);
        assertEquals(1, result.getProductOrders().size());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartService, times(1)).clearCarts();
    }

    @Test
    void testSumPriceAllOrders() {
        List<GetOrderDtoInAll> orders = Arrays.asList(new GetOrderDtoInAll(1L, 400.0, Arrays.asList(new GetOrderDtoInAll.ProductInOrderDto(1L, "Product 1", "image1.jpg", "Description", 2, 200.0))));

        // Call method
        double result = orderService.sumPriceAllOrder(orders);

        // Assert results
        assertEquals(400.0, result, 0.001);
    }
}

