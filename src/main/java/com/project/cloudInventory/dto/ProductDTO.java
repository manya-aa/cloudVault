package com.project.cloudInventory.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    private Long categoryId;
    private Long supplierId;

    private String imageUrl;
    private Integer stockQty;
}
