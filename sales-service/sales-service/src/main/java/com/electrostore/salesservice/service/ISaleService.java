package com.electrostore.salesservice.service;

import com.electrostore.salesservice.model.Sale;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISaleService {
    public Sale createSale(Long cartId);
    public List<Sale> getSales();
    public Sale findSaleById(Long id);
    public void deleteSale(Long id);
    public Sale updateSale(Long id, Sale sale);
}
