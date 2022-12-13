package com.quat.Kumquat.service;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Set<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products!=null)
            return products.stream()
                .map((product) -> mapToProductDto(product))
                .collect(Collectors.toSet());
        else
            return Collections.emptySet();
    }

    private ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setItemName(product.getItemName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(ProductDto prod) {
        if(productRepository.findById(prod.getId())!=null){

        }
        Product product = new Product();
        product.setPrice(prod.getPrice());
        product.setStock(prod.getStock());
        product.setImage(prod.getImage());
        String des = prod.getDescription();
        if(des!=null)
            product.setDescription(des);
        else
            product.setDescription("");
        product.setUsers(Collections.emptySet());
        productRepository.save(product);
    }

    @Override
    public void changeProduct(ProductDto product){
       Optional<Product> cp = productRepository.findById(product.getId());
       cp.ifPresent(product1 -> {
           product1.setPrice(product.getPrice());
           product1.setStock(product.getStock());
           product1.setImage(product.getImage());
           String des = product.getDescription();
           if(des!=null)
               product1.setDescription(des);
           else
               product1.setDescription("");
           productRepository.save(product1);
       });



    }
}
