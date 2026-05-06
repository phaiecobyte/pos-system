package com.phaiecobyte.pos.backend.auth.repository;

import com.phaiecobyte.pos.backend.auth.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // បន្ថែម @EntityGraph ដើម្បីបង្ខំឱ្យវាទាញយក roles និង permissions មកព្រមគ្នាតែម្តង
    // ជៀសវាងបញ្ហា no session ពេល Spring Security ត្រូវការផ្ទៀងផ្ទាត់សិទ្ធិ
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
