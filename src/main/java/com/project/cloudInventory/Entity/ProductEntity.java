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
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
private String description;
private Double price;

@ManyToOne
@JoinColumn(name="category_id")
private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "product")
    private List<StockEntity> stockMovements;


@ManyToOne
@JoinColumn(name="supplier_id")
private SupplierEntity supplier;
@Column(name="image_url")
private String imageUrl;
@Column(name="stock_qty")
    private Integer stockQty;


}
