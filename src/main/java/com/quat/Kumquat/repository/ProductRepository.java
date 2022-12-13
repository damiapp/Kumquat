package com.quat.Kumquat.repository;

import com.quat.Kumquat.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
