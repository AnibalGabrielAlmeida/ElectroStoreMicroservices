package com.electrostore.cartservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private Long productId;
    private int quantity;

}
