package com.quat.Kumquat.repository;

import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.ProductOrder;
import com.quat.Kumquat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<Product> findUserProducts(long user_id);
}
