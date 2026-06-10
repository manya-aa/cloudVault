package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
@Autowired
ProductRepository  productRepository;
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public void deleteProductById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.deleteById(id);
    }

    public ProductEntity updateProductById(Long id, ProductEntity productEntity) {
      ProductEntity product =  productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
product.setName(productEntity.getName());

      product.setPrice(productEntity.getPrice());
      product.setCategoryEntity(productEntity.getCategoryEntity());

      product.setDescription(productEntity.getDescription());
      product.setImageUrl(productEntity.getImageUrl());
      product.setStockQty(productEntity.getStockQty());
      product.setSupplier(productEntity.getSupplier());
      return productRepository.save(product);
    }
}

