package com.example.productapi.controller;

import com.example.productapi.dto.ProductResponse;
import com.example.productapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = Mockito.mock(ProductService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
    }

    @Test
    void testCreateProductEndpoint() throws Exception {
        ProductResponse resp = new ProductResponse(
                1L, "PROD00000000001", "Phone",
                BigDecimal.valueOf(100), BigDecimal.valueOf(110), "Desc");

        Mockito.when(productService.createProduct(any())).thenReturn(resp);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "code": "PROD00000000001",
                              "name": "Phone",
                              "priceEur": 100,
                              "description": "Desc"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"));
    }

    @Test
    void testGetProducts() throws Exception {
        Mockito.when(productService.findProducts(null, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPopular() throws Exception {
        Mockito.when(productService.findTop3Popular()).thenReturn(List.of());

        mockMvc.perform(get("/api/products/popular"))
                .andExpect(status().isOk());
    }
}
