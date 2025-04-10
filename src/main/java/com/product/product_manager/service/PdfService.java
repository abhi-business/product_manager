package com.product.product_manager.service;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.product.product_manager.model.Product;
import com.product.product_manager.repository.ProductRepository;

import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {
    
    private final ProductRepository productRepository;

    public PdfService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public byte[] generatePdf() {
        List<Product> products = productRepository.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

                document.add(new Paragraph("Product List").simulateBold().setFontSize(16));


            Table table = new Table(new float[]{1, 3, 2});
            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Price");

            for (Product product : products) {
                table.addCell(String.valueOf(product.getP_id()));
                table.addCell(product.getP_name());
                table.addCell(String.valueOf(product.getP_price()));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return outputStream.toByteArray();
    }
}
