package com.example.productapi.service;

import com.example.productapi.dto.PopularProductDto;
import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.entity.Product;
import com.example.productapi.entity.Review;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer handling product creation, searching, price conversion,
 * and review management.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final HnbService hnbService;

    /**
     * Creates a ProductService with required dependencies.
     *
     * @param productRepository repository for products
     * @param reviewRepository repository for product reviews
     * @param hnbService service for currency conversion
     */
    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository, HnbService hnbService) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.hnbService = hnbService;
    }

    /**
     * Creates and persists a new product.
     * Converts its EUR price to USD using HNB’s exchange rate.
     *
     * @param req the incoming product request DTO
     * @return the saved product as a response DTO
     */
    @Transactional
    public ProductResponse createProduct(ProductRequest req) {
        if (productRepository.findByCode(req.getCode()).isPresent()) {
            throw new IllegalArgumentException("Product with same code exists");
        }
        Product p = new Product();
        p.setCode(req.getCode());
        p.setName(req.getName());
        p.setPriceEur(req.getPriceEur());
        BigDecimal rate = hnbService.getEurToUsdRate();
        p.setPriceUsd(req.getPriceEur().multiply(rate).setScale(2, RoundingMode.HALF_UP));
        p.setDescription(req.getDescription());
        Product saved = productRepository.save(p);
        return new ProductResponse(saved.getId(), saved.getCode(), saved.getName(), saved.getPriceEur(), saved.getPriceUsd(), saved.getDescription());
    }

    /**
     * Searches for products by code, name, both, or returns all.
     *
     * @param code optional code filter
     * @param name optional name filter
     * @return list of product responses
     */
    public List<ProductResponse> findProducts(String code, String name) {
        List<Product> list;
        if (code != null && name != null) list = productRepository.findByCodeContainsIgnoreCaseAndNameContainsIgnoreCase(code, name);
        else if (code != null) list = productRepository.findByCodeContainsIgnoreCase(code);
        else if (name != null) list = productRepository.findByNameContainsIgnoreCase(name);
        else list = productRepository.findAll();
        return list.stream().map(p -> new ProductResponse(p.getId(), p.getCode(), p.getName(), p.getPriceEur(), p.getPriceUsd(), p.getDescription())).collect(Collectors.toList());
    }

    /**
     * Returns the top 3 products with the highest average rating.
     *
     * @return list of PopularProductDto (name + rating)
     */
    public List<PopularProductDto> findTop3Popular() {
        List<PopularProductDto> avgs = reviewRepository.findAverageRatingsPerProduct();
        return avgs.stream().limit(3).map(p -> new PopularProductDto(p.getName(), round(p.getAverageRating(), 1))).collect(Collectors.toList());
    }

    private double round(Double value, int decimals) {
        if (value == null) return 0.0;
        BigDecimal bd = BigDecimal.valueOf(value).setScale(decimals, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Adds a review to the given product.
     *
     * @param product the product being reviewed
     * @param reviewer the reviewer’s name
     * @param text the review text
     * @param rating the rating (1–5)
     */
    @Transactional
    public void addReview(Product product, String reviewer, String text, int rating) {
        Review r = new Review(product, reviewer, text, rating);
        r.setProduct(product);
        reviewRepository.save(r);
    }
}
