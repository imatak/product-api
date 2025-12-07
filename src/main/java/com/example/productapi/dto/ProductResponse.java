package com.example.productapi.dto;

import java.math.BigDecimal;

/**
 * Response DTO representing product details, including EUR and USD prices.
 *
 * @param id          Product ID
 * @param code        Unique product code
 * @param name        Product name
 * @param priceEur    Price in EUR
 * @param priceUsd    Price converted to USD
 * @param description Optional product description
 */
public record ProductResponse(
        Long id,
        String code,
        String name,
        BigDecimal priceEur,
        BigDecimal priceUsd,
        String description
) { }
