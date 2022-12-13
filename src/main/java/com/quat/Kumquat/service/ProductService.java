package com.quat.Kumquat.service;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.model.Product;

import java.util.Set;

public interface ProductService {
    public Set<ProductDto> getAllProducts();
    public Product getProduct(long id);
    public void saveProduct(ProductDto prod);
    public void changeProduct(ProductDto product);
}
