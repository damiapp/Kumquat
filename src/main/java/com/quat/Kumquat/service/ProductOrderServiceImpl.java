package com.quat.Kumquat.service;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.repository.ProductOrderRepository;
import com.quat.Kumquat.repository.ProductRepository;

import java.util.List;

public class ProductOrderServiceImpl implements ProductOrderService{
    private ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }


    @Override
    public List<Product> getAllProductsForUser(int user_id) {
        return productOrderRepository.findUserProducts(user_id);
    }
}
