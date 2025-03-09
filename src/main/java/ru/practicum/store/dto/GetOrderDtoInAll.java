package ru.practicum.store.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderDtoInAll {

    private long id;

    private double totalPrice;

    private List<ProductInOrderDto> products;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInOrderDto {

        private long id;

        private String title;

        private String image;

        private String description;

        private int quantity;

        private double price;
    }
}
