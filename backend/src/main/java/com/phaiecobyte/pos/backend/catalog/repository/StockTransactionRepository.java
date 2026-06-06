package com.phaiecobyte.pos.backend.product.repository;

import com.phaiecobyte.pos.backend.product.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, UUID> {
    // សម្រាប់អោយ Frontend ទាញមើលប្រវត្តិស្តុករបស់ទំនិញណាមួយ
    List<StockTransaction> findByProductIdOrderByCreatedAtDesc(UUID productId);
}