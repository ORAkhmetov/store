package ru.practicum.store.service;

import java.util.List;

import ru.practicum.store.dto.CreateOrderDto;
import ru.practicum.store.dto.GetOrderDtoInAll;
import ru.practicum.store.model.Order;

public interface OrderService {

    List<GetOrderDtoInAll> findAll();

    GetOrderDtoInAll findById(long id);

    Order save(CreateOrderDto dto);

    double sumPriceAllOrder(List<GetOrderDtoInAll> dtos);

}
