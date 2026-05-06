package com.phaiecobyte.pos.backend.core.repository;

import com.phaiecobyte.pos.backend.core.model.Product;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
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
    Page<Product> findByCategoryIdAndActiveTrue(UUID categoryId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")}) // កំណត់ត្រឹម 3000ms (3 វិនាទី)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithPessimisticLock(@Param("id") UUID id);
}