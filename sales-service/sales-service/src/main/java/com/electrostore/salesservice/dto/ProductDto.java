package com.electrostore.salesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private int quantity;
    private Double price;
}
