package com.project.cloudInventory.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEntity {
@Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    String name;
    String email;
    String phone;
    String address;


}
