package com.project.cloudInventory.repository;

import com.project.cloudInventory.Entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

    List<StockEntity> findByProductId(Long productId);

}