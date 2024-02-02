package com.electrostore.salesservice.controller;

import com.electrostore.salesservice.model.Sale;
import com.electrostore.salesservice.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleContoller {
    @Autowired
    ISaleService saleService;

    @PostMapping("/create")
    ResponseEntity<Void> createSale(@RequestBody Sale sale){
        saleService.createSale(sale);
        UriComponents uriComponents = UriComponentsBuilder.fromPath("/sale/{id}")
                .buildAndExpand(sale.getId());
        URI location =uriComponents.toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/list")
    ResponseEntity<List<Sale>> getAllSales(){
        List<Sale> sales = saleService.getSales();
        return ResponseEntity.ok(sales);
    }


    @GetMapping("/{id}")
    ResponseEntity<Sale> getSaleById(@PathVariable Long id){
        Sale sale = saleService.findSaleById(id);
        if (sale != null) {
            return ResponseEntity.ok(sale);
        }
        else {
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
