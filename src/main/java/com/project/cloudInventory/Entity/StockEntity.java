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
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product;

           @Column(name="qty_change")
    private int quantityChange;
    @Enumerated(EnumType.STRING)
    @Column(name="movement")
    private Movement movementType;
   private LocalDateTime timestamp;

}
