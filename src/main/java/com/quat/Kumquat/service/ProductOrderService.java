package com.quat.Kumquat.service;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.model.Product;

import java.util.List;

public interface ProductOrderService {
    public List<Product> getAllProductsForUser(int user_id);
}
