package com.project.cloudInventory.dto;

import lombok.Data;

@Data
public class SupplierDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}