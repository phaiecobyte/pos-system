package com.phaiecobyte.pos.backend.core.entity;

import com.phaiecobyte.pos.backend.core.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "t_core_audit_logs")
@Getter @Setter
public class AuditLog extends BaseEntity {
    @Column(nullable = false)
    private String action; // ឧទាហរណ៍៖ CREATE, UPDATE, DELETE, VOID [៣១]

    @Column(nullable = false)
    private String moduleName; // ឧទាហរណ៍៖ SALES, INVENTORY, AUTH [១២៤]

    @Column(nullable = false)
    private String entityName; // ឈ្មោះ Table ដែលរងការប្រែប្រួល

    private UUID entityId; // ID នៃទិន្នន័យដែលត្រូវបានកែប្រែ

    @Column(nullable = false)
    private UUID userId; // អ្នកដែលជាអ្នកអនុវត្តសកម្មភាពនេះ [៣៦]

    @Column(columnDefinition = "TEXT")
    private String oldValue; // តម្លៃដើមមុនពេលកែ (រក្សាទុកជា JSON)

    @Column(columnDefinition = "TEXT")
    private String newValue; // តម្លៃថ្មីក្រោយពេលកែ (រក្សាទុកជា JSON)

    @Column(columnDefinition = "TEXT")
    private String reason; // មូលហេតុនៃការកែប្រែ (តម្រូវជាដាច់ខាតពេលលុបវិក្កយបត្រ) [៣១]

    private String ipAddress; // សម្រាប់តាមដានសន្តិសុខបន្ថែម
}
