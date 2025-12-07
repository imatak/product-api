package com.example.productapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request DTO for creating or updating a product.
 * Includes product code, name, price in EUR, and optional description.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequest {

    @NotBlank
    @Size(min = 15, max = 15)
    private String code;

    @NotBlank
    private String name;

    @DecimalMin(value = "0.0")
    private BigDecimal priceEur;

    private String description;
}
