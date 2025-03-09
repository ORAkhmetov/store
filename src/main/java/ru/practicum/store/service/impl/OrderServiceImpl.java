package ru.practicum.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.store.converter.OrderConverter;
import ru.practicum.store.dto.CreateOrderDto;
import ru.practicum.store.dto.CreateOrderDto.ProductInOrderDto;
import ru.practicum.store.dto.GetOrderDtoInAll;
import ru.practicum.store.exception.EntityNotFoundException;
import ru.practicum.store.model.Order;
import ru.practicum.store.model.Product;
import ru.practicum.store.model.ProductOrder;
import ru.practicum.store.repository.OrderRepository;
import ru.practicum.store.repository.ProductOrderRepository;
import ru.practicum.store.repository.ProductRepository;
import ru.practicum.store.service.CartService;
import ru.practicum.store.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final ProductOrderRepository productOrderRepository;

    private final OrderConverter orderConverter;

    private final CartService cartService;

    @Override
    public List<GetOrderDtoInAll> findAll() {
        return orderRepository.findAll().stream()
                .map(orderConverter::toGetOrderDtoInAll)
                .toList();
    }

    @Override
    public GetOrderDtoInAll findById(long id) {
        return orderRepository.findById(id)
                .map(orderConverter::toGetOrderDtoInAll)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public Order save(CreateOrderDto dto) {
        Order order = new Order();

        List<ProductOrder> productOrders = new ArrayList<>();

        for (ProductInOrderDto productDto : dto.getProducts()) {
            Product product = productRepository.findById(productDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setOrder(order);
            productOrder.setPrice(product.getPrice());
            productOrder.setQuantity(productDto.getQuantity());
            productOrders.add(productOrder);
        }
        order.setProductOrders(productOrders);

        cartService.clearCarts();
        return orderRepository.save(order);
    }

    @Override
    public double sumPriceAllOrder(List<GetOrderDtoInAll> dtos) {
        return dtos.stream().map(GetOrderDtoInAll::getProducts)
                .flatMap(List::stream)
                .mapToDouble(productInOrder -> productInOrder.getPrice() * productInOrder.getQuantity())
                .sum();
    }
}
