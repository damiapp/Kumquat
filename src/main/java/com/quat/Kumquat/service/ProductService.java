package com.quat.Kumquat.service;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    public List<ProductDto> getAllProducts();
    public ProductDto findProductByName(String prodName);
    public ProductDto getProduct(long id);
    public void saveProduct(ProductDto prod);
    public void changeProduct(int id, ProductDto product);
}
