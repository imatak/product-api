package com.example.productapi.controller;

import com.example.productapi.dto.PopularProductDto;
import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing products.
 * Provides endpoints to create products, search products, and get popular products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     *
     * @param req the product request payload
     * @return the created product as a response DTO
     */
    @Operation(
            summary = "Create a new product",
            description = "Creates a product with EUR price and converts it to USD. Returns the created product.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product created successfully",
                            content = @Content(schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(productService.createProduct(req));
    }

    /**
     * Searches for products by code and/or name.
     *
     * @param code optional code filter
     * @param name optional name filter
     * @return list of products matching the filters
     */
    @Operation(
            summary = "Search products",
            description = "Searches products by partial code and/or name. Case-insensitive.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products found",
                            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductResponse>> search(
            @Parameter(description = "Partial product code", example = "PROD00000000001")
            @RequestParam(name = "code", required = false) String code,
            @Parameter(description = "Partial product name", example = "Samsung")
            @RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(productService.findProducts(code, name));
    }

    /**
     * Returns the top 3 popular products.
     *
     * @return map containing a list of top 3 popular products and average review
     */
    @Operation(
            summary = "Get top 3 popular products and average review",
            description = "Returns the top 3 products sorted by average review rating.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Popular products retrieved",
                            content = @Content(schema = @Schema(implementation = PopularProductDto.class)))
            }
    )
    @GetMapping("/popular")
    public ResponseEntity<Map<String, List<PopularProductDto>>> popular() {
        List<PopularProductDto> list = productService.findTop3Popular();
        Map<String, List<PopularProductDto>> resp = new HashMap<>();
        resp.put("popularProducts", list);
        return ResponseEntity.ok(resp);
    }
}
