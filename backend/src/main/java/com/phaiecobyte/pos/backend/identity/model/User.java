package com.phaiecobyte.pos.backend.identity.model;

import com.phaiecobyte.pos.backend.core.persistence.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "t_identity_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_tenant_username",
                        columnNames = {
                                "tenant_id",
                                "username"
                        }
                )
        }
)
public class User extends TenantAwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username",nullable = false, length = 55)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name",length = 55)
    private String firstName;

    @Column(name = "last_name", length = 55)
    private String lastName;

    @Column(name = "email",unique = true,length = 55)
    private String email;

    @Column(name = "phone", unique = true,length = 20)
    private String phone;

    @Column(name = "is_active")
    private boolean isActive = true;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_identity_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Role> roles;

}