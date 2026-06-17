package com.project.cloudInventory.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cloudInventory.dto.ProductDTO;
import com.project.cloudInventory.response.ApiResponse;
import com.project.cloudInventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;
    @Test
    void getProductById_success() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Laptop");
        dto.setPrice(BigDecimal.valueOf(50000));

        when(productService.getProductById(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    void saveProduct_success() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Laptop");
        dto.setPrice(BigDecimal.valueOf(50000));

        when(productService.saveProduct(any(ProductDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    void updateProduct_success() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Updated Laptop");

        when(productService.updateProductById(eq(1L), any(ProductDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Laptop"));
    }

    @Test
    void deleteProduct_success() throws Exception {

        doNothing().when(productService).deleteProductById(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getAllProducts_success() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Laptop");

        when(productService.getAllProducts(0, 5))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/api/products?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].name").value("Laptop"));
    }

    @Test
    void searchProducts_success() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Laptop");

        when(productService.searchProducts(
                "Electronics",
                "Dell",
                10,
                50
        )).thenReturn(List.of(dto));

        mockMvc.perform(
                        get("/api/products/search")
                                .param("category", "Electronics")
                                .param("supplier", "Dell")
                                .param("minStock", "10")
                                .param("maxStock", "50")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Laptop"));
    }
}