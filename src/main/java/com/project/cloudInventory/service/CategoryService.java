package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.CategoryEntity;
import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.repository.CategoryRepository;
import com.project.cloudInventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public CategoryEntity saveCategory(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        categoryRepository.deleteById(id);
    }

    public CategoryEntity updateCategoryById(Long id, CategoryEntity categoryEntity) {
CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

category.setName(categoryEntity.getName());
category.setId(categoryEntity.getId());
category.setParent_category_id(categoryEntity.getParent_category_id());

        return categoryRepository.save(category);
    }
}
