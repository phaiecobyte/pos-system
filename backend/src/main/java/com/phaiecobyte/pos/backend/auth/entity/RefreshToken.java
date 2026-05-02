package com.phaiecobyte.pos.backend.auth.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name = "t_auth_refresh_token")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}