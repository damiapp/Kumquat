package com.quat.Kumquat.service;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.User;
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
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map((product) -> mapToProductDto(product))
                .collect(Collectors.toList());
    }

    private ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setItemName(product.getItemName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setStock(product.getStock());
        productDto.setImage(product.getImage());
        return productDto;
    }

    public ProductDto findProductByName(String prodName){ return mapToProductDto(productRepository.findByItemName(prodName));}

    @Override
    public ProductDto getProduct(long id) {
        return mapToProductDto(productRepository.findById(id));
    }

    @Override
    public void saveProduct(ProductDto prod) {
        if(productRepository.findByItemName(prod.getItemName())==null){
            Product product = new Product();
            product.setItemName(prod.getItemName());
            product.setPrice(prod.getPrice());
            product.setStock(prod.getStock());
            product.setImage(prod.getImage());
            String des = prod.getDescription();
            if(des!=null)
                product.setDescription(des);
            else
                product.setDescription("");
            productRepository.save(product);
        }
    }

    @Override
    public void changeProduct(int id, ProductDto product){
       Product cp = productRepository.findById(id);
       productRepository.delete(cp);
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
}
