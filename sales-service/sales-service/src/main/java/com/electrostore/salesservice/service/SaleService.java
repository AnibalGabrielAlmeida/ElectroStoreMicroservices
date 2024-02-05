package com.electrostore.salesservice.service;

import com.electrostore.salesservice.dto.CartDto;
import com.electrostore.salesservice.model.Sale;
import com.electrostore.salesservice.repository.SaleRepository;

import com.electrostore.salesservice.repository.ShoppingCartServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    private ShoppingCartServiceClient shoppingCartService;

    @Override
    public Sale createSale(Long cartId) {
        // Get the shopping cart
        CartDto cartDto = shoppingCartService.getCartInfo(cartId);

        // Create the sale and associate it with the shopping cart
        Sale sale = new Sale();
        sale.setDate(LocalDate.now());
        sale.setShoppingCartId(cartId);

        // Save the sale to the database
        Sale savedSale = saleRepository.save(sale);


        return savedSale;
    }


    @Override
    public List<Sale> getSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale findSaleById(Long id) {
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public Sale updateSale(Long id, Sale sale) {
        Sale existingSale = saleRepository.findById(id).orElse(null);
        if(existingSale != null && sale != null){
            existingSale.setDate(sale.getDate());
            existingSale.setShoppingCartId(sale.getShoppingCartId());
            saleRepository.save(existingSale);
        }
        return existingSale;
    }
}
