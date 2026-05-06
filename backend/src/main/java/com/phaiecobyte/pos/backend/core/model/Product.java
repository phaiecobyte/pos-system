package com.phaiecobyte.pos.backend.core.model;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "t_core_product")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code; // ឧ. Barcode ឬ លេខកូដទំនិញ

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // តម្លៃលក់ចេញ

    // តើវាជាទំនិញដែលត្រូវកាត់ស្តុក (រាយ) ឬគ្រាន់តែជាសេវាកម្ម/កាហ្វេ (មិនកាត់ស្តុក)?
    @Column(name = "is_stockable", nullable = false)
    private boolean stockable = true; 

    // ចំនួនក្នុងស្តុកបច្ចុប្បន្ន (ប្រើសម្រាប់តែទំនិញ stockable = true)
    @Column(name = "current_stock")
    private Integer currentStock;

    @Column(name = "is_active")
    private boolean active = true;

    // ទំនាក់ទំនង Many-To-One ជាមួយតារាង Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;
}