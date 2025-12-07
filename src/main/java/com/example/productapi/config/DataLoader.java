package com.example.productapi.config;

import com.example.productapi.dto.ProductRequest;
import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.repository.ReviewRepository;
import com.example.productapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Loads sample products and reviews into the database at application startup.
 * Runs only if no products exist.
 */
@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() > 0) return;

        ProductRequest r1 = new ProductRequest();
        r1.setCode("PROD00000000001");
        r1.setName("Samsung Galaxy S23");
        r1.setPriceEur(new BigDecimal("699.00"));
        r1.setDescription("Flagship phone");

        ProductRequest r2 = new ProductRequest();
        r2.setCode("PROD00000000002");
        r2.setName("iPhone SE");
        r2.setPriceEur(new BigDecimal("399.00"));
        r2.setDescription("Compact iPhone");

        ProductRequest r3 = new ProductRequest();
        r3.setCode("PROD00000000003");
        r3.setName("Xiaomi 13");
        r3.setPriceEur(new BigDecimal("499.00"));
        r3.setDescription("Good midrange");

        ProductRequest r4 = new ProductRequest();
        r4.setCode("PROD00000000004");
        r4.setName("OnePlus 11");
        r4.setPriceEur(new BigDecimal("549.00"));
        r4.setDescription("Speedy phone");

        ProductRequest r5 = new ProductRequest();
        r5.setCode("PROD00000000005");
        r5.setName("Google Pixel 7");
        r5.setPriceEur(new BigDecimal("599.00"));
        r5.setDescription("Pure Android");

        var resp1 = productService.createProduct(r1);
        var resp2 = productService.createProduct(r2);
        var resp3 = productService.createProduct(r3);
        var resp4 = productService.createProduct(r4);
        var resp5 = productService.createProduct(r5);

        Product p1 = productRepository.findByCode(resp1.code()).orElseThrow();
        Product p2 = productRepository.findByCode(resp2.code()).orElseThrow();
        Product p3 = productRepository.findByCode(resp3.code()).orElseThrow();
        Product p4 = productRepository.findByCode(resp4.code()).orElseThrow();
        Product p5 = productRepository.findByCode(resp5.code()).orElseThrow();

        productService.addReview(p1, "Alice","Great product!",5);
        productService.addReview(p1, "Bob","Good value.",4);
        productService.addReview(p1, "Carol", "Excellent!", 5);
        productService.addReview(p1, "Diana", "Pretty good.", 5);

        productService.addReview(p2, "Alice","Excellent for size.",5);
        productService.addReview(p2, "Bob","Solid performer.",4);
        productService.addReview(p2, "Carol", "Wow!", 5);
        productService.addReview(p2, "Diana", "Very good.", 5);;

        productService.addReview(p3, "Alice","Very good value.",5);
        productService.addReview(p3, "Bob","Nice camera.",4);

        productService.addReview(p4, "Alice","Smooth performance.",3);
        productService.addReview(p4, "Bob","Battery fine.",2);

        productService.addReview(p5, "Alice","Best Android UX.",2);
        productService.addReview(p5, "Bob","Lovely photos.",2);
    }
}