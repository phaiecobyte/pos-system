package com.phaiecobyte.pos.backend.core.repository;

import com.phaiecobyte.pos.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByCode(String code);
    boolean existsByCode(String code);
    
    // សម្រាប់ឱ្យ Frontend ទាញយកទំនិញតាមប្រភេទ (ឧ. ទាញយកតែភេសជ្ជៈ)
    List<Product> findByCategoryIdAndActiveTrue(UUID categoryId); 
}