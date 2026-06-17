package com.project.cloudInventory.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(name="name",nullable = false,unique = true)
    private String categoryName;

private String description;

    @OneToMany(mappedBy = "categoryEntity")
    private List<ProductEntity> products;
}