package com.project.cloudInventory.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column(nullable = false,unique = true)
   private String name;
private String description;
@Column(nullable = false)
private BigDecimal price;

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
