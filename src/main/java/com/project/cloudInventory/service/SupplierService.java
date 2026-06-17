package com.project.cloudInventory.service;

import com.project.cloudInventory.Entity.SupplierEntity;
import com.project.cloudInventory.dto.SupplierDTO;
import com.project.cloudInventory.repository.SupplierRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // ---------------- MAPPER ----------------

    public static SupplierDTO toDTO(SupplierEntity entity) {
        if (entity == null) return null;

        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());

        return dto;
    }

    public static SupplierEntity toEntity(SupplierDTO dto) {
        if (dto == null) return null;

        SupplierEntity entity = new SupplierEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());

        return entity;
    }

    // ---------------- CREATE ----------------

    @Caching(
            put = {
                    @CachePut(value = "supplier", key = "#result.id")
            },
            evict = {
                    @CacheEvict(value = "supplierList", allEntries = true)
            }
    )
    public SupplierDTO createSupplier(SupplierDTO dto) {
        SupplierEntity saved = supplierRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    // ---------------- GET ALL ----------------

    @Cacheable(value = "supplierList")
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierService::toDTO)
                .toList();
    }

    // ---------------- GET BY ID ----------------

    @Cacheable(value = "supplier", key = "#id")
    public SupplierDTO getSupplierById(Long id) {
        SupplierEntity entity = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        return toDTO(entity);
    }

    // ---------------- UPDATE ----------------

    @Caching(
            put = {
                    @CachePut(value = "supplier", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "supplierList", allEntries = true)
            }
    )
    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {

        SupplierEntity existing = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());

        SupplierEntity saved = supplierRepository.save(existing);

        return toDTO(saved);
    }

    // ---------------- DELETE ----------------

    @Caching(
            evict = {
                    @CacheEvict(value = "supplier", key = "#id"),
                    @CacheEvict(value = "supplierList", allEntries = true)
            }
    )
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}