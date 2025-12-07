package com.example.productapi.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object representing a popular product with its name and average rating.
 */
@JsonPropertyOrder({"name", "averageRating"})
@AllArgsConstructor
@Getter
@Setter
public class PopularProductDto {
    private String name;
    private Double averageRating;
}
