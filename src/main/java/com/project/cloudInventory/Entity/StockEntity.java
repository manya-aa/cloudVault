package com.project.cloudInventory.Entity;

import com.project.cloudInventory.Enum.Movement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private ProductEntity product;

           @Column(name="qty_change",nullable = false)
    private int quantityChange;
    @Enumerated(EnumType.STRING)
    @Column(name="movement",nullable = false)
    private Movement movementType;
   private LocalDateTime timestamp;

}
