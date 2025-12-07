package com.example.productapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a product in the catalog.
 *
 * <p>This entity stores basic product information such as code, name,
 * price in EUR/USD, and description. It also maintains a list of related
 * product reviews.</p>
 *
 * <p>The product code is unique and acts as a business identifier.</p>
 *
 * <p>Reviews are cascaded and removed automatically when detached from the product.</p>
 */
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(exclude = "reviews")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    /**
     * Primary key for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    /**
     * Unique business code (15 characters).
     */
    @Column(length = 15, nullable = false, unique = true)
    private String code;

    /**
     * Name of the product.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Price of the product in EUR.
     */
    private BigDecimal priceEur;

    /**
     * Price of the product in USD (converted from EUR).
     */
    private BigDecimal priceUsd;

    /**
     * Product description
     */
    private String description;

    /**
     * List of reviews associated with the product.
     * <p>
     * - {@code cascade = ALL} ensures reviews are persisted/removed with the product.
     * - {@code orphanRemoval = true} deletes reviews removed from this list.
     * </p>
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
