package com.electrostore.salesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class CartDto {

    private Long id;
    private Double totalPrice;
    private List<Long> listProductIds;
}
