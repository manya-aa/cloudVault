package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.dto.ProductDTO;
import com.project.cloudInventory.repository.CategoryRepository;
import com.project.cloudInventory.repository.ProductRepository;
import com.project.cloudInventory.repository.SupplierRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    // ---------- ENTITY → DTO ----------
    private ProductDTO toDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        dto.setStockQty(entity.getStockQty());

        if (entity.getCategoryEntity() != null) {
            dto.setCategoryId(entity.getCategoryEntity().getId());
        }

        if (entity.getSupplier() != null) {
            dto.setSupplierId(entity.getSupplier().getId());
        }

        return dto;
    }

    // ---------- DTO → ENTITY ----------
    private ProductEntity toEntity(ProductDTO dto) {

        ProductEntity entity = new ProductEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());
        entity.setStockQty(dto.getStockQty());

        if (dto.getCategoryId() != null) {
            entity.setCategoryEntity(
                    categoryRepository.findById(dto.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"))
            );
        }

        if (dto.getSupplierId() != null) {
            entity.setSupplier(
                    supplierRepository.findById(dto.getSupplierId())
                            .orElseThrow(() -> new RuntimeException("Supplier not found"))
            );
        }

        return entity;
    }

    // ---------- GET ALL ----------
    @Cacheable(value = "productList", key = "#page + '-' + #size")
    public Page<ProductDTO> getAllProducts(int page, int size) {
        return productRepository.findAll(
                        PageRequest.of(page, size, Sort.by("name").ascending())
                )
                .map(this::toDTO);
    }

    // ---------- GET BY ID ----------
    @Cacheable(value = "product", key = "#id")
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // ---------- CREATE ----------
    @Caching(
            put = {
                    @CachePut(value = "product", key = "#result.id")
            },
            evict = {
                    @CacheEvict(value = "productList", allEntries = true)
            }
    )
    public ProductDTO saveProduct(ProductDTO dto) {
        ProductEntity saved = productRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    // ---------- UPDATE ----------
    @Caching(
            put = {
                    @CachePut(value = "product", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "productList", allEntries = true)
            }
    )
    public ProductDTO updateProductById(Long id, ProductDTO dto) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setStockQty(dto.getStockQty());

        if (dto.getCategoryId() != null) {
            product.setCategoryEntity(
                    categoryRepository.findById(dto.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"))
            );
        }

        if (dto.getSupplierId() != null) {
            product.setSupplier(
                    supplierRepository.findById(dto.getSupplierId())
                            .orElseThrow(() -> new RuntimeException("Supplier not found"))
            );
        }

        ProductEntity saved = productRepository.save(product);
        return toDTO(saved);
    }

    // ---------- DELETE ----------
    @Caching(
            evict = {
                    @CacheEvict(value = "product", key = "#id"),
                    @CacheEvict(value = "productList", allEntries = true)
            }
    )
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    // ---------- SEARCH ----------
    @Cacheable(
            value = "productSearch",
            key = "#category + '-' + #supplier + '-' + #minStock + '-' + #maxStock"
    )
    public List<ProductDTO> searchProducts(String category,
                                           String supplier,
                                           Integer minStock,
                                           Integer maxStock) {

        return productRepository.searchProducts(category, supplier, minStock, maxStock)
                .stream()
                .map(this::toDTO)
                .toList();
    }
}