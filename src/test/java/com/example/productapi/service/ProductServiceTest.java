package com.example.productapi.service;

import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.entity.Product;
import com.example.productapi.entity.Review;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private HnbService hnbService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequest req = new ProductRequest();
        req.setCode("PROD00000000001");
        req.setName("Phone");
        req.setPriceEur(BigDecimal.valueOf(100));

        when(productRepository.findByCode("PROD00000000001"))
                .thenReturn(Optional.empty());
        when(hnbService.getEurToUsdRate()).thenReturn(BigDecimal.valueOf(1.10));

        Product saved = new Product();
        saved.setCode(req.getCode());
        saved.setName(req.getName());
        saved.setPriceEur(req.getPriceEur());
        saved.setPriceUsd(BigDecimal.valueOf(110));
        saved.setDescription(null);

        when(productRepository.save(any(Product.class)))
                .thenReturn(saved);

        ProductResponse response = productService.createProduct(req);

        assertEquals("Phone", response.name());
        assertEquals("PROD00000000001", response.code());
        assertEquals(BigDecimal.valueOf(110), response.priceUsd());
    }

    @Test
    void testFindProducts() {
        Product p = new Product();
        p.setCode("ABC");
        p.setName("Test");

        when(productRepository.findAll()).thenReturn(List.of(p));

        List<ProductResponse> list = productService.findProducts(null, null);
        assertEquals(1, list.size());
        assertEquals("Test", list.get(0).name());
    }

    @Test
    void testAddReview() {
        Product p = new Product();
        p.setName("Phone");

        Review saved = new Review(p, "John", "Great", 5);

        when(reviewRepository.save(any())).thenReturn(saved);

        productService.addReview(p, "John", "Great", 5);

        verify(reviewRepository, times(1)).save(any());
    }
}
