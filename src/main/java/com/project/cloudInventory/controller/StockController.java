package com.project.cloudInventory.controller;

import com.project.cloudInventory.dto.StockDTO;
import com.project.cloudInventory.response.ApiResponse;
import com.project.cloudInventory.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StockDTO>> createStockMovement(
            @RequestBody StockDTO stock) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "stock added", stockService.createStockMovement(stock)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockDTO>>> getAllStockMovements() {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "stocks listed", stockService.getAllStockMovements())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockDTO>> getStockMovementById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "stock found", stockService.getStockMovementById(id))
        );
    }

    @GetMapping("/history/{productId}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> getStockHistory(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "history of product", stockService.getStockHistory(productId))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStockMovement(
            @PathVariable Long id) {

        stockService.deleteStockMovement(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "stock deleted", null));
    }
}