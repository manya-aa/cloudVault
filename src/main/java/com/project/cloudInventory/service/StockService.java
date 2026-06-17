package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.Entity.StockEntity;
import com.project.cloudInventory.dto.StockDTO;
import com.project.cloudInventory.repository.ProductRepository;
import com.project.cloudInventory.repository.StockRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockService(StockRepository stockRepository,
                        ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    // ---------- ENTITY → DTO ----------
    private StockDTO toDTO(StockEntity entity) {
        StockDTO dto = new StockDTO();
        dto.setId(entity.getId());
        dto.setQuantityChange(entity.getQuantityChange());
        dto.setMovementType(entity.getMovementType());
        dto.setTimestamp(entity.getTimestamp());

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getId());
        }

        return dto;
    }

    // ---------- DTO → ENTITY ----------
    private StockEntity toEntity(StockDTO dto) {
        StockEntity entity = new StockEntity();

        entity.setId(dto.getId());
        entity.setQuantityChange(dto.getQuantityChange());
        entity.setMovementType(dto.getMovementType());
        entity.setTimestamp(dto.getTimestamp());

        if (dto.getProductId() != null) {
            ProductEntity product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            entity.setProduct(product);
        }

        return entity;
    }

    // ---------- CREATE ----------
    @Caching(evict = {
            @CacheEvict(value = "stockList", allEntries = true),
            @CacheEvict(value = "stockHistory", allEntries = true)
    })
    public StockDTO createStockMovement(StockDTO dto) {
        StockEntity saved = stockRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    // ---------- GET ALL ----------
    @Cacheable(value = "stockList")
    public List<StockDTO> getAllStockMovements() {
        return stockRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ---------- GET BY ID ----------
    @Cacheable(value = "stock", key = "#id")
    public StockDTO getStockMovementById(Long id) {
        return stockRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    // ---------- HISTORY ----------
    @Cacheable(value = "stockHistory", key = "#productId")
    public List<StockDTO> getStockHistory(Long productId) {
        return stockRepository.findByProductId(productId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ---------- DELETE ----------
    @Caching(evict = {
            @CacheEvict(value = "stock", key = "#id"),
            @CacheEvict(value = "stockList", allEntries = true),
            @CacheEvict(value = "stockHistory", allEntries = true)
    })
    public void deleteStockMovement(Long id) {
        stockRepository.deleteById(id);
    }
}