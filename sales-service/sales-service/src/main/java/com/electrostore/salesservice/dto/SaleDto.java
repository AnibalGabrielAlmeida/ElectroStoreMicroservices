package com.electrostore.salesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class SaleDto {
    private Long saleId;
    private List<Long> productsId;
    private List<String> productsName;
    private Double totalAmount;

}
