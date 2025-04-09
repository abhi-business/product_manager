package com.product.product_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.product_manager.model.Product;
import com.product.product_manager.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productrepository;

    public ProductService(ProductRepository productrepository) {
        this.productrepository = productrepository;
    }

    // Saves a single product into database
    public Product saveProduct(Product product) {
        return productrepository.save(product);
    }

    // View a single product from database
    public Product getProductbyId(Long p_id) {
        return productrepository.findById(p_id).orElse(null);
    }

    // View all products from database
    public List<Product> getAllProducts() {
        return productrepository.findAll();
    }

    // Update a single product into database
    public Product updateProduct(Long p_id, Product updatedProduct) {
        if (productrepository.existsById(p_id)) {
            updatedProduct.setP_id(p_id);
            return productrepository.save(updatedProduct);
        }
        return null;
    }

    // Delete a single product from database
    public void deleteProduct(Long p_id) {
        productrepository.deleteById(p_id);
    }
}
