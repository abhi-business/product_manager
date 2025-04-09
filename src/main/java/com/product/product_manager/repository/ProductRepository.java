package com.product.product_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.product_manager.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
