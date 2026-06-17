package com.project.cloudInventory.controller;

import com.project.cloudInventory.dto.SupplierDTO;
import com.project.cloudInventory.response.ApiResponse;
import com.project.cloudInventory.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierDTO>> createSupplier(@RequestBody SupplierDTO supplier) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "supplier added", supplierService.createSupplier(supplier)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierDTO>>> getAllSuppliers() {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "all suppliers", supplierService.getAllSuppliers())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierDTO>> getSupplierById(@PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "supplier found", supplierService.getSupplierById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierDTO>> updateSupplier(
            @PathVariable Long id,
            @RequestBody SupplierDTO supplier) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "supplier updated", supplierService.updateSupplier(id, supplier))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(@PathVariable Long id) {

        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "supplier deleted", null));
    }
}