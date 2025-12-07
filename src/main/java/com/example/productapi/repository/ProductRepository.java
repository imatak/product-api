package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Product} entities.
 *
 * <p>Provides basic CRUD operations through {@link JpaRepository}
 * and includes custom search queries for filtering products
 * by code and/or name (case-insensitive).</p>
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds a product by its unique code.
     *
     * @param code the product code
     * @return an Optional containing the product if found
     */
    Optional<Product> findByCode(String code);

    /**
     * Searches for products where both the code and name
     * contain the given text (case-insensitive).
     *
     * @param code partial or full code text
     * @param name partial or full name text
     * @return matching products
     */
    @Query("""
           select p from Product p
           where lower(p.code) like lower(concat('%', :code, '%'))
             and lower(p.name) like lower(concat('%', :name, '%'))
           """)
    List<Product> findByCodeContainsIgnoreCaseAndNameContainsIgnoreCase(
            @Param("code") String code,
            @Param("name") String name);

    /**
     * Searches for products where the code contains the given text (case-insensitive).
     *
     * @param code partial or full code text
     * @return matching products
     */
    @Query("""
           select p from Product p
           where lower(p.code) like lower(concat('%', :code, '%'))
           """)
    List<Product> findByCodeContainsIgnoreCase(@Param("code") String code);

    /**
     * Searches for products where the name contains the given text (case-insensitive).
     *
     * @param name partial or full name text
     * @return matching products
     */
    @Query("""
           select p from Product p
           where lower(p.name) like lower(concat('%', :name, '%'))
           """)
    List<Product> findByNameContainsIgnoreCase(@Param("name") String name);
}
