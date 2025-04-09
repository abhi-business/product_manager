package com.product.product_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.product_manager.model.Product;
import com.product.product_manager.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productservice;

    public ProductController(ProductService productService) {
        this.productservice = productService;
    }

    // Saving a product data %
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productservice.saveProduct(product);
        return "redirect:/products";
    }

    //Add a product data
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product", new Product());
        return "product_form";
    }   

    // View all product data %
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productservice.getAllProducts());
        return "products";
    }

    // Update a product data %
    @GetMapping("/editproduct/{p_id}")
    public String editProductForm(@PathVariable Long p_id, Model model) {
        model.addAttribute("product", productservice.getProductbyId(p_id));
        return "product_form";
    }

    // Deleting a product data %
    @GetMapping("/deleteproduct/{p_id}")
    public String deleteProduct(@PathVariable Long p_id) {
        productservice.deleteProduct(p_id);
        return "redirect:/products";
    }
}
