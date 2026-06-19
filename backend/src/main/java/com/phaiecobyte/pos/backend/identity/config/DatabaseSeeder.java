package com.phaiecobyte.pos.backend.identity.config;

import com.phaiecobyte.pos.backend.identity.model.Role;
import com.phaiecobyte.pos.backend.identity.model.User;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
// អ្នកនឹងត្រូវការបង្កើត RoleRepository ប្រសិនបើមិនទាន់មាន
import com.phaiecobyte.pos.backend.identity.repository.RoleRepository;

import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UUID tenantId = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342ac");

    @Override
    public void run(String... args) throws Exception {

        // ១. បង្កើត Role SUPER_ADMIN ប្រសិនបើមិនទាន់មានក្នុង Database
        Role superAdminRole = roleRepository.findByName("SUPER_ADMIN").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("SUPER_ADMIN");
            newRole.setDescription("អ្នកគ្រប់គ្រងប្រព័ន្ធកំពូល (មានសិទ្ធិពេញលេញ)");
            newRole.setTenantId(tenantId);
            return roleRepository.save(newRole);
        });

        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ADMIN");
            newRole.setDescription("អ្នកគ្រប់គ្រងប្រព័ន្ធ (មានសិទ្ធិពេញលេញ)");
            newRole.setTenantId(tenantId);
            return roleRepository.save(newRole);
        });

        Role cashier = roleRepository.findByName("CASHIER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("CASHIER");
            newRole.setDescription("អ្នកគិតលុយ");
            newRole.setTenantId(tenantId);
            return roleRepository.save(newRole);
        });

        // ២. ត្រួតពិនិត្យ និងបង្កើត User "admin"
        if (userRepository.findByUsername("admin").isEmpty()) {
            User superAdmin = new User();
            superAdmin.setUsername("admin");
            // ប្រើ PasswordEncoder ដើម្បី Hash លេខសម្ងាត់ (ឧ. លេខសម្ងាត់គឺ admin123)
            superAdmin.setPassword(passwordEncoder.encode("admin123"));
            superAdmin.setFirstName("Super Administrator");
            superAdmin.setFirstName("Phal");
            superAdmin.setLastName("Phai");
            superAdmin.setActive(true);
            superAdmin.setTenantId(tenantId);

            superAdmin.setRoles(Set.of(superAdminRole,adminRole,cashier));

            userRepository.save(superAdmin);
            System.out.println("✅ ទិន្នន័យ Super Admin ត្រូវបានបញ្ចូលដោយជោគជ័យ! (Username: admin | Password: admin123)");
        } else {
            System.out.println("⚡ Super Admin មានរួចហើយនៅក្នុង Database មិនចាំបាច់បង្កើតថ្មីទេ។");
        }
    }
}