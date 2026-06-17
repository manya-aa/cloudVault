package com.project.cloudInventory.controller;

import com.project.cloudInventory.dto.CategoryDTO;
import com.project.cloudInventory.response.ApiResponse;
import com.project.cloudInventory.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All Categories",
                        categoryService.getAllCategories(page, size))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category found",
                        categoryService.getCategoryById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> saveCategory(
            @RequestBody CategoryDTO categoryDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Category added successfully",
                        categoryService.saveCategory(categoryDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategoryById(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category updated successfully",
                        categoryService.updateCategoryById(id, categoryDTO))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@PathVariable Long id) {

        categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category Deleted", null)
        );
    }
}