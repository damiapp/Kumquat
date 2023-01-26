package com.quat.Kumquat.service;

import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.ProductOrder;
import com.quat.Kumquat.repository.ProductOrderRepository;
import com.quat.Kumquat.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ProductOrderRepository productOrderRepository;

    public ProductServiceImpl(ProductRepository productRepository
                                , ProductOrderRepository productOrderRepository)
    {
        this.productRepository = productRepository;
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }


    public Product findProductByName(String prodName){ return productRepository.findByItemName(prodName);}

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(Product prod) {
        if(productRepository.findByItemName(prod.getItemName())==null){
            productRepository.save(prod);
        }
    }

    @Override
    public void changeProduct(int id, Product product){
        Product cp = productRepository.findById(id);
        cp.setItemName(product.getItemName());
        cp.setPrice(product.getPrice());
        cp.setStock(product.getStock());
        cp.setImage(product.getImage());

        String des = cp.getDescription();
        if(des!=null)
           cp.setDescription(des);
        else
           cp.setDescription("");

        if(cp.getImage()!=null)
           cp.setImage(product.getImage());
        productRepository.save(cp);

    }

    @Override
    public Product findProduct(long id) {
        return productRepository.findById(id);
    }
}
