package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setCode("PROD0001");
        product.setName("Phone");
        product.setPriceEur(BigDecimal.valueOf(100));
        product.setDescription("Desc");

        productRepository.save(product);

        assertThat(productRepository.findById(product.getId())).isPresent();
    }

    @Test
    void testFindAllProducts() {
        Product product1 = new Product();
        product1.setCode("PROD0002");
        product1.setName("Laptop");
        product1.setPriceEur(BigDecimal.valueOf(200));
        product1.setDescription("Laptop Desc");

        productRepository.save(product1);

        assertThat(productRepository.findAll()).isNotEmpty();
    }
}
