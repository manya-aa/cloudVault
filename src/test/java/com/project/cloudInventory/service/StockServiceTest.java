package com.project.cloudInventory.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.Entity.StockEntity;
import com.project.cloudInventory.Enum.Movement;
import com.project.cloudInventory.dto.StockDTO;
import com.project.cloudInventory.repository.ProductRepository;
import com.project.cloudInventory.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    StockRepository stockRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    StockService stockService;

    @Test
    void createStockMovement_success() {

        LocalDateTime now = LocalDateTime.now();

        StockDTO dto = new StockDTO();
        dto.setProductId(1L);
        dto.setQuantityChange(10);
        dto.setMovementType(Movement.IN);
        dto.setTimestamp(now);

        ProductEntity product = new ProductEntity();
        product.setId(1L);

        StockEntity saved = new StockEntity();
        saved.setId(100L);
        saved.setProduct(product);
        saved.setQuantityChange(10);
        saved.setMovementType(Movement.IN);
        saved.setTimestamp(now);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(stockRepository.save(any(StockEntity.class)))
                .thenReturn(saved);

        StockDTO result = stockService.createStockMovement(dto);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(10, result.getQuantityChange());
        assertEquals(Movement.IN, result.getMovementType());

        verify(productRepository).findById(1L);
        verify(stockRepository).save(any(StockEntity.class));
    }

    @Test
    void createStockMovement_productNotFound() {

        StockDTO dto = new StockDTO();
        dto.setProductId(99L);

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> stockService.createStockMovement(dto)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(stockRepository, never()).save(any());
    }

    @Test
    void getStockMovementById_success() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);

        StockEntity stock = new StockEntity();
        stock.setId(10L);
        stock.setProduct(product);
        stock.setQuantityChange(5);
        stock.setMovementType(Movement.OUT);

        when(stockRepository.findById(10L))
                .thenReturn(Optional.of(stock));

        StockDTO result = stockService.getStockMovementById(10L);

        assertEquals(10L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(5, result.getQuantityChange());

        verify(stockRepository).findById(10L);
    }

    @Test
    void getStockMovementById_notFound() {

        when(stockRepository.findById(5L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> stockService.getStockMovementById(5L)
        );

        assertEquals("Stock not found", exception.getMessage());
    }

    @Test
    void getStockHistory_success() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);

        StockEntity stock = new StockEntity();
        stock.setId(1L);
        stock.setProduct(product);
        stock.setQuantityChange(20);
        stock.setMovementType(Movement.IN);

        when(stockRepository.findByProductId(1L))
                .thenReturn(List.of(stock));

        List<StockDTO> result = stockService.getStockHistory(1L);

        assertEquals(1, result.size());
        assertEquals(20, result.get(0).getQuantityChange());
        assertEquals(1L, result.get(0).getProductId());

        verify(stockRepository).findByProductId(1L);
    }

    @Test
    void deleteStockMovement_success() {

        stockService.deleteStockMovement(1L);

        verify(stockRepository).deleteById(1L);
    }
}