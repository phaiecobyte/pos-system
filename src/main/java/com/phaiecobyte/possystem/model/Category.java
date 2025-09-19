package com.phaiecobyte.possystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity(name = "tbl_category")
@Getter
@Setter
public class Category extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 50)
    private String name;
    @Column(length = 1000)
    private String description;

    @OneToMany
    @JoinColumn(name = "category_id")
    private Set<Item> items;
}
