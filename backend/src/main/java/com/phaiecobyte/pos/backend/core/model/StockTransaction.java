package com.phaiecobyte.pos.backend.core.model;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import com.phaiecobyte.pos.backend.core.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_core_stock_transaction")
@Getter @Setter
public class StockTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // ចំនួនដែលបូក ឬដក

    @Column(name = "remark")
    private String remark; // មូលហេតុ (ឧ. "ទិញចូលពីផ្សារធំថ្មី")
}