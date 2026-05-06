package com.phaiecobyte.pos.backend.auth.repository;

import com.phaiecobyte.pos.backend.auth.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    // មិនចាំបាច់បន្ថែម Method អ្វីទេ JpaRepository មាន existsById ស្រាប់ហើយ
}