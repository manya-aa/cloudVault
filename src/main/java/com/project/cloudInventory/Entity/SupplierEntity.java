package com.project.cloudInventory.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier")
public class SupplierEntity {
@Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    private String address;

    @JsonIgnore
@OneToMany(mappedBy = "supplier")
    private List<ProductEntity> products;
}
