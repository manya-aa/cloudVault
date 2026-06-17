package com.project.cloudInventory.dto;


import com.project.cloudInventory.Enum.Movement;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockDTO {

    private Long id;

    private Long productId;

    private int quantityChange;

    private Movement movementType;

    private LocalDateTime timestamp;
}