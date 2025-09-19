package com.phaiecobyte.possystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_user")
@Getter
@Setter
public class User extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String fullName;
}
