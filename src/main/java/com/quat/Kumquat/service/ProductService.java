package com.quat.Kumquat.service;

import com.quat.Kumquat.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product findProductByName(String prodName);
    public Product getProduct(long id);
    public void saveProduct(Product prod);
    public void changeProduct(int id, Product product);
    Product findProduct(long id);
}
