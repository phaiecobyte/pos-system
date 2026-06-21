package com.phaiecobyte.pos.backend.identity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phaiecobyte.pos.backend.core.persistence.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "t_identity_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_role_tenant_name",
                        columnNames = {
                                "tenant_id",
                                "code"
                        }
                )
        }
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends TenantAwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code", nullable = false, length = 55)
    private String code;

    @Column(length = 55)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_system")
    private boolean isSystem = false;

    //===================== RELATIONSHIP ==========================
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_identity_role_perm",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties
    private Set<Permission> permissions;
}