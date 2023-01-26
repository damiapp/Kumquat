package com.quat.Kumquat.service;

import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.ProductOrder;
import com.quat.Kumquat.model.User;
import com.quat.Kumquat.repository.ProductOrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductOrderServiceImpl implements ProductOrderService{
    private ProductOrderRepository productOrderRepository;
    private ProductService productService;
    private UserService userService;

    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository,
                                   ProductService productService) {
        this.productOrderRepository = productOrderRepository;
        this.productService = productService;
    }


    public void setStatusSent(long id){
        ProductOrder po = productOrderRepository.findById(id).get();
        po.setStatus(false);
        productOrderRepository.save(po);
    }

    @Override
    public void addProductForUser(User user, Product product) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setUser(user);
        productOrder.setStatus(true);
        productOrderRepository.save(productOrder);
    }

    public List<ProductOrder> findAllProductOrdersForUser(User user){
        List<ProductOrder> productOrders = productOrderRepository.findAllByUser(user);
        return productOrders;
    }

    public List<ProductOrder> findAll(){
        return productOrderRepository.findAll();
    }
}
