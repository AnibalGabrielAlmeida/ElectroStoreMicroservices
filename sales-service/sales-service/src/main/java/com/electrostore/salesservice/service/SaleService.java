package com.electrostore.salesservice.service;

import com.electrostore.salesservice.model.Sale;
import com.electrostore.salesservice.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    SaleRepository saleRepository;

    @Override
    public void createSale(Sale sale) {
        saleRepository.save(sale);
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
            createSale(existingSale);
        }
        return existingSale;
    }
}
