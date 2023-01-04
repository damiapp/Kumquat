package com.quat.Kumquat.repository;

import com.quat.Kumquat.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByItemName(String item_name);
    Product findById(long id);
}
