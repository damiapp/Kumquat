package com.quat.Kumquat.service;

import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.ProductOrder;
import com.quat.Kumquat.model.User;

import java.util.List;

public interface ProductOrderService {
    public void setStatusSent(long id);
    User findUserWithProdOrdId(long id);
    void addProductForUser(User user, Product product);
    List<ProductOrder> findAllProductOrdersForUser(User user);
    List<ProductOrder> findAll();
    ProductOrder findById(long id);
}
