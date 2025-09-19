package com.phaiecobyte.possystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_order_detail")
@Getter
@Setter
public class OrderDetail extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;
    private double subTotal;

}
