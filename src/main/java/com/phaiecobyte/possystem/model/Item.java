package com.phaiecobyte.possystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_item")
@Getter
@Setter
public class Item extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double priceIn;
    private double priceOut;
    private int stock;
    @Column(length = 1000)
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
