package com.project.cloudInventory.service;

import static org.junit.jupiter.api.Assertions.*;

import com.project.cloudInventory.Entity.CategoryEntity;
import com.project.cloudInventory.dto.CategoryDTO;
import com.project.cloudInventory.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void saveCategory_success() {

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName("Electronics");
        dto.setDescription("Electronic Items");

        CategoryEntity saved = new CategoryEntity();
        saved.setId(1L);
        saved.setCategoryName("Electronics");
        saved.setDescription("Electronic Items");

        when(categoryRepository.save(any(CategoryEntity.class)))
                .thenReturn(saved);

        CategoryDTO result = categoryService.saveCategory(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Electronics", result.getCategoryName());
        assertEquals("Electronic Items", result.getDescription());

        verify(categoryRepository).save(any(CategoryEntity.class));
    }

    @Test
    void getCategoryById_success() {

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);
        category.setCategoryName("Electronics");
        category.setDescription("Electronic Items");

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        CategoryDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Electronics", result.getCategoryName());
        assertEquals("Electronic Items", result.getDescription());

        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategoryById_notFound() {

        when(categoryRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.getCategoryById(99L)
        );

        assertEquals(
                "Category not found with id: 99",
                exception.getMessage()
        );

        verify(categoryRepository).findById(99L);
    }

    @Test
    void updateCategoryById_success() {

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName("Updated");
        dto.setDescription("Updated Description");

        CategoryEntity existing = new CategoryEntity();
        existing.setId(1L);
        existing.setCategoryName("Old Category");
        existing.setDescription("Old Description");

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(categoryRepository.save(any(CategoryEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CategoryDTO result = categoryService.updateCategoryById(1L, dto);

        assertEquals("Updated", result.getCategoryName());
        assertEquals("Updated Description", result.getDescription());

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(any(CategoryEntity.class));
    }

    @Test
    void updateCategoryById_notFound() {

        when(categoryRepository.findById(5L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.updateCategoryById(5L, new CategoryDTO())
        );

        assertEquals(
                "Category not found with id: 5",
                exception.getMessage()
        );

        verify(categoryRepository).findById(5L);
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void deleteCategoryById_success() {

        categoryService.deleteCategoryById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}