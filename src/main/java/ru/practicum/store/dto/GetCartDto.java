package ru.practicum.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCartDto {

    private long id;

    private String productTitle;

    private String productImage;

    private String productDescription;

    private long productPrice;

    private long quantity;
}
