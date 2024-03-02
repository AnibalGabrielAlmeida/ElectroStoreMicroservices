package com.electrostore.cartservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
/*
    @ElementCollection
    private List<Long> listProductIds;
*/
@ElementCollection
@CollectionTable(name = "product_quantity_map", joinColumns = @JoinColumn(name = "cart_id"))
@MapKeyColumn(name = "product_id")
@Column(name = "quantity")
private Map<Long, Integer> productQuantityMap = new HashMap<>();
    private double totalPrice;


    public void updateTotalPrice() {
    }
}
