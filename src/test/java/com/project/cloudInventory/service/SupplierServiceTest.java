package com.project.cloudInventory.service;

import static org.junit.jupiter.api.Assertions.*;
import com.project.cloudInventory.Entity.SupplierEntity;
import com.project.cloudInventory.dto.SupplierDTO;
import com.project.cloudInventory.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    SupplierRepository supplierRepository;

    @InjectMocks
    SupplierService supplierService;

    @Test
    void createSupplier_success() {

        SupplierDTO dto = new SupplierDTO();
        dto.setName("ABC Suppliers");
        dto.setEmail("abc@test.com");
        dto.setPhone("9876543210");
        dto.setAddress("Delhi");

        SupplierEntity saved = new SupplierEntity();
        saved.setId(1L);
        saved.setName("ABC Suppliers");
        saved.setEmail("abc@test.com");
        saved.setPhone("9876543210");
        saved.setAddress("Delhi");

        when(supplierRepository.save(any(SupplierEntity.class)))
                .thenReturn(saved);

        SupplierDTO result = supplierService.createSupplier(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ABC Suppliers", result.getName());
        assertEquals("abc@test.com", result.getEmail());

        verify(supplierRepository).save(any(SupplierEntity.class));
    }

    @Test
    void getAllSuppliers_success() {

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(1L);
        supplier.setName("ABC Suppliers");
        supplier.setEmail("abc@test.com");
        supplier.setPhone("9876543210");
        supplier.setAddress("Delhi");

        when(supplierRepository.findAll())
                .thenReturn(List.of(supplier));

        List<SupplierDTO> result = supplierService.getAllSuppliers();

        assertEquals(1, result.size());
        assertEquals("ABC Suppliers", result.get(0).getName());
        assertEquals("abc@test.com", result.get(0).getEmail());

        verify(supplierRepository).findAll();
    }

    @Test
    void getSupplierById_success() {

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(1L);
        supplier.setName("ABC Suppliers");
        supplier.setEmail("abc@test.com");
        supplier.setPhone("9876543210");
        supplier.setAddress("Delhi");

        when(supplierRepository.findById(1L))
                .thenReturn(Optional.of(supplier));

        SupplierDTO result = supplierService.getSupplierById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ABC Suppliers", result.getName());

        verify(supplierRepository).findById(1L);
    }

    @Test
    void getSupplierById_notFound() {

        when(supplierRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> supplierService.getSupplierById(99L)
        );

        assertEquals(
                "Supplier not found with id: 99",
                exception.getMessage()
        );

        verify(supplierRepository).findById(99L);
    }

    @Test
    void updateSupplier_success() {

        SupplierDTO dto = new SupplierDTO();
        dto.setName("Updated Supplier");
        dto.setEmail("updated@test.com");
        dto.setPhone("9999999999");
        dto.setAddress("Mumbai");

        SupplierEntity existing = new SupplierEntity();
        existing.setId(1L);
        existing.setName("Old Supplier");

        when(supplierRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(supplierRepository.save(any(SupplierEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SupplierDTO result = supplierService.updateSupplier(1L, dto);

        assertEquals("Updated Supplier", result.getName());
        assertEquals("updated@test.com", result.getEmail());
        assertEquals("9999999999", result.getPhone());
        assertEquals("Mumbai", result.getAddress());

        verify(supplierRepository).findById(1L);
        verify(supplierRepository).save(any(SupplierEntity.class));
    }

    @Test
    void updateSupplier_notFound() {

        when(supplierRepository.findById(5L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> supplierService.updateSupplier(5L, new SupplierDTO())
        );

        assertEquals(
                "Supplier not found with id: 5",
                exception.getMessage()
        );

        verify(supplierRepository).findById(5L);
        verify(supplierRepository, never()).save(any(SupplierEntity.class));
    }

    @Test
    void deleteSupplier_success() {

        supplierService.deleteSupplier(1L);

        verify(supplierRepository, times(1)).deleteById(1L);
    }
}