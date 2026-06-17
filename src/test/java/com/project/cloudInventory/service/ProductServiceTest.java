package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.CategoryEntity;
import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.Entity.SupplierEntity;
import com.project.cloudInventory.dto.ProductDTO;
import com.project.cloudInventory.repository.CategoryRepository;
import com.project.cloudInventory.repository.ProductRepository;
import com.project.cloudInventory.repository.SupplierRepository;
import com.project.cloudInventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    SupplierRepository supplierRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void saveProduct_success() {

        ProductDTO dto = new ProductDTO();
        dto.setName("Laptop");
        dto.setDescription("Gaming");
        dto.setPrice(BigDecimal.valueOf(50000));
        dto.setCategoryId(1L);
        dto.setSupplierId(2L);
        dto.setImageUrl("img");
        dto.setStockQty(20);

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(2L);

        ProductEntity savedEntity = new ProductEntity();
        savedEntity.setId(100L);
        savedEntity.setName(dto.getName());
        savedEntity.setDescription(dto.getDescription());
        savedEntity.setPrice(dto.getPrice());
        savedEntity.setCategoryEntity(category);
        savedEntity.setSupplier(supplier);
        savedEntity.setImageUrl(dto.getImageUrl());
        savedEntity.setStockQty(dto.getStockQty());

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(supplierRepository.findById(2L))
                .thenReturn(Optional.of(supplier));

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(savedEntity);

        ProductDTO result = productService.saveProduct(dto);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(BigDecimal.valueOf(50000), result.getPrice());

        verify(productRepository, times(1))
                .save(any(ProductEntity.class));
    }

    @Test
    void getProductById_success() {

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(2L);

        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setName("Laptop");
        product.setDescription("Gaming");
        product.setPrice(BigDecimal.valueOf(50000));
        product.setCategoryEntity(category);
        product.setSupplier(supplier);
        product.setImageUrl("img");
        product.setStockQty(20);

        when(productRepository.findById(10L))
                .thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(1L, result.getCategoryId());
        assertEquals(2L, result.getSupplierId());

        verify(productRepository).findById(10L);
    }@Test
    void getProductById_notFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.getProductById(99L)
        );

        assertEquals(
                "Product not found with id: 99",
                exception.getMessage()
        );

        verify(productRepository).findById(99L);
    }
    @Test
    void updateProductById_success() {

        ProductDTO dto = new ProductDTO();
        dto.setName("MacBook");
        dto.setDescription("Apple Laptop");
        dto.setPrice(BigDecimal.valueOf(80000));
        dto.setCategoryId(1L);
        dto.setSupplierId(2L);
        dto.setImageUrl("image");
        dto.setStockQty(15);

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(2L);

        ProductEntity existing = new ProductEntity();
        existing.setId(1L);
        existing.setName("Old Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(supplierRepository.findById(2L))
                .thenReturn(Optional.of(supplier));

        when(productRepository.save(any(ProductEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductDTO result = productService.updateProductById(1L, dto);

        assertEquals("MacBook", result.getName());
        assertEquals(BigDecimal.valueOf(80000), result.getPrice());
        assertEquals(15, result.getStockQty());
        assertEquals(1L, result.getCategoryId());
        assertEquals(2L, result.getSupplierId());

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(ProductEntity.class));
    }
    @Test
    void deleteProductById_success() {

        productService.deleteProductById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
    @Test
    void searchProducts_success() {

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(2L);

        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(50000));
        product.setCategoryEntity(category);
        product.setSupplier(supplier);
        product.setStockQty(20);

        when(productRepository.searchProducts(
                "Electronics",
                "Dell",
                10,
                50
        )).thenReturn(List.of(product));

        List<ProductDTO> result = productService.searchProducts(
                "Electronics",
                "Dell",
                10,
                50
        );

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals(1L, result.get(0).getCategoryId());
        assertEquals(2L, result.get(0).getSupplierId());

        verify(productRepository).searchProducts(
                "Electronics",
                "Dell",
                10,
                50
        );
    }
    @Test
    void saveProduct_categoryNotFound() {

        ProductDTO dto = new ProductDTO();
        dto.setCategoryId(100L);

        when(categoryRepository.findById(100L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.saveProduct(dto)
        );

        assertEquals("Category not found", exception.getMessage());
    }
    @Test
    void saveProduct_supplierNotFound() {

        ProductDTO dto = new ProductDTO();
        dto.setCategoryId(1L);
        dto.setSupplierId(200L);

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(supplierRepository.findById(200L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.saveProduct(dto)
        );

        assertEquals("Supplier not found", exception.getMessage());
    }
}