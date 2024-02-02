package com.electrostore.salesservice.service;

import com.electrostore.salesservice.model.Sale;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISaleService {
    public void createSale(Sale sale);
    public List<Sale> getSales();
    public Sale findSaleById(Long id);
    public void deleteSale(Long id);
    public Sale updateSale(Long id, Sale sale);
}
