package com.product.product_manager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.product_manager.model.Product;
import com.product.product_manager.service.PdfService;
import com.product.product_manager.service.ProductExcelExporter;
import com.product.product_manager.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productservice;

    @Autowired
    private ProductExcelExporter productExcelExporter;

    @Autowired
    private PdfService pdfservice;

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





    //Creating Excel File
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

        List<Product> productList = productservice.getAllProducts();
        productExcelExporter.export(productList, response);
    }

    //Creating PDF File
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        byte[] pdfBytes = pdfservice.generatePdf();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.pdf")
                .body(pdfBytes);
    }

}
