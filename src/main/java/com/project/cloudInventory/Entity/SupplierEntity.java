package com.project.cloudInventory.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEntity {
@Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

@OneToMany(mappedBy = "supplier")
    private List<ProductEntity> products;
}
