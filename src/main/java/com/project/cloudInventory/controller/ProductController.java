package com.project.cloudInventory.controller;

import com.project.cloudInventory.dto.ProductDTO;
import com.project.cloudInventory.response.ApiResponse;
import com.project.cloudInventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "all the products",
                        productService.getAllProducts(page, size))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product fetched successfully",
                        productService.getProductById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> saveProduct(
            @RequestBody ProductDTO productDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Product added successfully",
                        productService.saveProduct(productDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable Long id) {

        productService.deleteProductById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product Deleted", null)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProductById(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product Updated",
                        productService.updateProductById(id, productDTO))
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Integer maxStock) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product searched successfully",
                        productService.searchProducts(category, supplier, minStock, maxStock))
        );
    }
}