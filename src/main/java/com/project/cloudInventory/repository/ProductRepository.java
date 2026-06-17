package com.project.cloudInventory.repository;

import com.project.cloudInventory.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query("""
        SELECT p
        FROM ProductEntity p
        WHERE
            (:category IS NULL OR p.categoryEntity.categoryName = :category)
            AND (:supplier IS NULL OR p.supplier.name = :supplier)
            AND (:minStock IS NULL OR p.stockQty >= :minStock)
            AND (:maxStock IS NULL OR p.stockQty <= :maxStock)
    """)
    List<ProductEntity> searchProducts(
            @Param("category") String category,
            @Param("supplier") String supplier,
            @Param("minStock") Integer minStock,
            @Param("maxStock") Integer maxStock
    );
}