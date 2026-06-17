package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.CategoryEntity;
import com.project.cloudInventory.dto.CategoryDTO;
import com.project.cloudInventory.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ---------- MAPPERS ----------
    public static CategoryDTO toDTO(CategoryEntity entity) {
        if (entity == null) return null;

        return new CategoryDTO(
                entity.getId(),
                entity.getCategoryName(),
                entity.getDescription()
        );
    }

    public static CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    // ---------- GET ALL ----------
    @Cacheable(value = "categoryList", key = "#page + '-' + #size")
    public Page<CategoryDTO> getAllCategories(int page, int size) {
        return categoryRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "categoryName"))
        ).map(CategoryService::toDTO);
    }

    // ---------- GET BY ID ----------
    @Cacheable(value = "category", key = "#id")
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryService::toDTO)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // ---------- CREATE ----------
    @CachePut(value = "category", key = "#result.id")
    @CacheEvict(value = "categoryList", allEntries = true)
    public CategoryDTO saveCategory(CategoryDTO dto) {
        CategoryEntity saved = categoryRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    // ---------- UPDATE ----------
    @Caching(
            put = {
                    @CachePut(value = "category", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "categoryList", allEntries = true)
            }
    )
    public CategoryDTO updateCategoryById(Long id, CategoryDTO dto) {

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        return toDTO(categoryRepository.save(category));
    }

    // ---------- DELETE ----------
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "categoryList", allEntries = true)
    })
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}