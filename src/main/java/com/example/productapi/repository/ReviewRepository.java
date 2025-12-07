package com.example.productapi.repository;

import com.example.productapi.entity.Review;
import com.example.productapi.dto.PopularProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for managing {@link Review} entities.
 *
 * <p>Includes a custom query that calculates the average rating
 * for each product and returns it as {@link PopularProductDto} entries.</p>
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Computes the average rating per product and returns a list
     * of DTOs sorted by highest rating first.
     *
     * @return list of products with their average ratings
     */
    @Query("""
           SELECT new com.example.productapi.dto.PopularProductDto(
               r.product.name,
               avg(r.rating)
           )
           FROM Review r
           GROUP BY r.product.id, r.product.name
           ORDER BY avg(r.rating) DESC
           """)
    List<PopularProductDto> findAverageRatingsPerProduct();
}
