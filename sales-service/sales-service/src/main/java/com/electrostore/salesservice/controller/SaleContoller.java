package com.electrostore.salesservice.controller;

import com.electrostore.salesservice.dto.CartDto;
import com.electrostore.salesservice.dto.ProductDto;
import com.electrostore.salesservice.dto.SaleDto;
import com.electrostore.salesservice.model.Sale;
import com.electrostore.salesservice.repository.ProductServiceClient;
import com.electrostore.salesservice.repository.ShoppingCartServiceClient;
import com.electrostore.salesservice.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
public class SaleContoller {
    @Autowired
    ISaleService saleService;

    @Autowired
    ShoppingCartServiceClient shoppingCartService;

    @Autowired
    ProductServiceClient productServiceClient;

    @PostMapping("/create/{cartId}")
    public ResponseEntity<Sale> createSale(@PathVariable Long cartId) {
        Sale createdSale = saleService.createSale(cartId);
        return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
    }


    @GetMapping("/list")
    ResponseEntity<List<Sale>> getAllSales(){
        List<Sale> sales = saleService.getSales();
        return ResponseEntity.ok(sales);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> getSaleById(@PathVariable Long id) {
        Sale sale = saleService.findSaleById(id);

        if (sale != null) {
            // Get information about the cart associated with the sale
            CartDto cart = shoppingCartService.getCartInfo(sale.getShoppingCartId());

            List<Long> productIds = cart.getListProductIds();
            System.out.println("Product IDs from cart: " + productIds);

            // Log the totalAmount of the cart
            System.out.println("Total Amount from cart: " + cart.getTotalPrice());

            List<ProductDto> productsL = productServiceClient.getProductInfo(productIds);
            System.out.println("Calling productServiceClient.getProductInfo with productIds: " + productIds);
            System.out.println("Received products from productServiceClient: " + productsL);

            // Get detailed information about the products
            List<ProductDto> products = productServiceClient.getProductInfo(cart.getListProductIds());

            // Create a SaleDto with the required information
            SaleDto saleDto = new SaleDto();
            saleDto.setSaleId(sale.getId());
            saleDto.setProductsId(cart.getListProductIds());
            saleDto.setProductsName(products.stream().map(ProductDto::getName).collect(Collectors.toList()));
            saleDto.setTotalAmount(cart.getTotalPrice());

            return ResponseEntity.ok(saleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteSale(@PathVariable Long id){
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Sale> updateSale(@PathVariable Long id,
                                    @RequestBody Sale updatedSale){
        Sale sale = saleService.updateSale(id, updatedSale);
        if(sale != null){
            return ResponseEntity.ok(sale);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

}
