package com.phaiecobyte.pos.backend.core.service.impl;

import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.dto.StockReq;
import com.phaiecobyte.pos.backend.core.enums.TransactionType;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import com.phaiecobyte.pos.backend.core.mapper.ProductMapper;
import com.phaiecobyte.pos.backend.core.model.Product;
import com.phaiecobyte.pos.backend.core.model.StockTransaction;
import com.phaiecobyte.pos.backend.core.repository.ProductRepository;
import com.phaiecobyte.pos.backend.core.repository.StockTransactionRepository;
import com.phaiecobyte.pos.backend.core.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;
    private final StockTransactionRepository stockTransactionRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional // ធានាសុវត្ថិភាពទិន្នន័យ (All or Nothing)
    public ProductRes processStock(StockReq request) {
        
        // ១. ស្វែងរកទំនិញ
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));

        // ២. ឆែកមើលថាតើទំនិញនេះអនុញ្ញាតឱ្យកាត់ស្តុកឬទេ? (បើជាកាហ្វេ មិនអាចបញ្ជូលស្តុកបានទេ)
        if (!product.isStockable()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "ទំនិញនេះមិនមែនជាប្រភេទកាត់ស្តុកទេ (Non-stockable)!");
        }

        // ៣. គណនាស្តុកថ្មី
        int currentStock = product.getCurrentStock() != null ? product.getCurrentStock() : 0;
        
        if (request.getType() == TransactionType.IN || request.getType() == TransactionType.ADJUSTMENT) {
            product.setCurrentStock(currentStock + request.getQuantity());
        } else if (request.getType() == TransactionType.OUT || request.getType() == TransactionType.SALE) {
            // ឆែកកុំឱ្យកាត់ស្តុកដល់អវិជ្ជមាន
            if (currentStock < request.getQuantity()) {
                throw new AppException(HttpStatus.BAD_REQUEST, "ចំនួនស្តុកក្នុងប្រព័ន្ធមិនគ្រប់គ្រាន់សម្រាប់ដកចេញទេ!");
            }
            product.setCurrentStock(currentStock - request.getQuantity());
        }

        // ៤. កត់ត្រាចូលក្នុងប្រវត្តិ StockTransaction
        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(product);
        transaction.setType(request.getType());
        transaction.setQuantity(request.getQuantity());
        transaction.setRemark(request.getRemark());
        
        stockTransactionRepository.save(transaction);
        
        // ៥. Update ទំនិញ និងបញ្ជូនទិន្នន័យថ្មីទៅ Frontend
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }
}