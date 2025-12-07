package com.example.productapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents a user review for a product.
 *
 * <p>Each review includes the reviewer's name, a text comment,
 * and a rating between 1 and 5. Reviews belong to a single product.</p>
 */
@Entity
@Table(name = "review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review {

    /**
     * Primary key for the review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <p>Product that this review belongs to.
     * Loaded lazily to avoid unnecessary joins.</p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    /**
     * Name of the reviewer.
     */
    private String reviewer;

    /**
     * Text content of the review
     */
    private String text;

    /**
     * Star rating (1 to 5 inclusive).
     */
    @Min(1)
    @Max(5)
    private Integer rating;

    /**
     * Convenience constructor for quick creation.
     */
    public Review(Product product, String reviewer, String text, Integer rating) {
        this.product = product;
        this.reviewer = reviewer;
        this.text = text;
        this.rating = rating;
    }
}
