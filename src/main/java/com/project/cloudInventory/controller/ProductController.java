package com.project.cloudInventory.controller;

import com.project.cloudInventory.Entity.ProductEntity;
import com.project.cloudInventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping
    ResponseEntity<List<ProductEntity>> getAllProducts(){
        List<ProductEntity> list = productService.getAllProducts();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductEntity> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    ResponseEntity<ProductEntity> saveProduct(@RequestBody ProductEntity productEntity){
        return ResponseEntity.ok(productService.saveProduct(productEntity));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductEntity> updateProductById(@PathVariable Long id, @RequestBody ProductEntity productEntity){
        return ResponseEntity.ok(productService.updateProductById(id,productEntity));
    }

}
