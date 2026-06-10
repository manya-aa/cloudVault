package com.project.cloudInventory.controller;

import com.project.cloudInventory.Entity.CategoryEntity;
import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.service.CategoryService;
import com.project.cloudInventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping
    ResponseEntity<List<CategoryEntity>> getAllCategories(){
        List<CategoryEntity> list = categoryService.getAllCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    ResponseEntity<CategoryEntity> saveCategory(@RequestBody CategoryEntity categoryEntity){
        return ResponseEntity.ok(categoryService.saveCategory(categoryEntity));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryEntity> updateCategoryById(@PathVariable Long id, @RequestBody CategoryEntity categoryEntity){
        return ResponseEntity.ok(categoryService.updateCategoryById(id,categoryEntity));
    }

}
