package com.phaiecobyte.pos.backend.core.service.impl;

import com.phaiecobyte.pos.backend.core.annotation.LogAudit;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;
    private final StockTransactionRepository stockTransactionRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    @LogAudit(action = "STOCK_UPDATE", moduleName = "STOCK", entityName = "t_core_product", defaultReason = "ធ្វើប្រតិបត្តិការកាត់/បន្ថែមស្តុក")
    public ProductRes processStock(StockReq request) {

        // ប្រើ Pessimistic Lock ដើម្បីការពារការកាត់ស្តុកជាន់គ្នា (Race Condition)
        Product product = productRepository.findByIdWithPessimisticLock(request.getProductId())
                .orElseThrow(() -> {
                    log.error("ប្រតិបត្តិការស្តុកបរាជ័យ - រកមិនឃើញទំនិញ ID: {}", request.getProductId());
                    return new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!");
                });

        if (!product.isStockable()) {
            log.warn("ប៉ុនប៉ងកាត់ស្តុកទំនិញដែលមិនមានស្តុក: {}", product.getName());
            throw new AppException(HttpStatus.BAD_REQUEST, "ទំនិញនេះមិនមែនជាប្រភេទកាត់ស្តុកទេ (Non-stockable)!");
        }

        int currentStock = product.getCurrentStock() != null ? product.getCurrentStock() : 0;

        if (request.getType() == TransactionType.IN || request.getType() == TransactionType.ADJUSTMENT) {
            product.setCurrentStock(currentStock + request.getQuantity());
            log.info("បញ្ចូលស្តុកចំនួន {} ទៅឱ្យទំនិញ {} - ស្តុកថ្មី: {}", request.getQuantity(), product.getName(), product.getCurrentStock());
        } else if (request.getType() == TransactionType.OUT || request.getType() == TransactionType.SALE) {
            if (currentStock < request.getQuantity()) {
                log.error("ស្តុកមិនគ្រប់គ្រាន់សម្រាប់ទំនិញ {} - មាន: {}, ស្នើសុំដក: {}", product.getName(), currentStock, request.getQuantity());
                throw new AppException(HttpStatus.BAD_REQUEST, "ចំនួនស្តុកក្នុងប្រព័ន្ធមិនគ្រប់គ្រាន់សម្រាប់ដកចេញទេ!");
            }
            product.setCurrentStock(currentStock - request.getQuantity());
            log.info("ដកស្តុកចំនួន {} ពីទំនិញ {} - ស្តុកសល់: {}", request.getQuantity(), product.getName(), product.getCurrentStock());
        }

        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(product);
        transaction.setType(request.getType());
        transaction.setQuantity(request.getQuantity());
        transaction.setRemark(request.getRemark());

        stockTransactionRepository.save(transaction);
        return productMapper.toResponse(productRepository.save(product));
    }
}