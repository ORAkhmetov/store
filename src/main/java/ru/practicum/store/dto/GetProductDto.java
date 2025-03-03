package ru.practicum.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDto {

    private long id;

    private String title;

    private String image;

    private double price;

    private String description;
}
