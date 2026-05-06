package com.phaiecobyte.pos.backend.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "t_auth_invalidated_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvalidatedToken {

    // យើងប្រើ JTI (JWT ID) ធ្វើជា Primary Key ឬប្រើ Token ផ្ទាល់ក៏បាន
    // ប៉ុន្តែប្រើ String Token ផ្ទាល់គឺស្រួលបំផុតសម្រាប់ការចាប់ផ្តើម
    @Id
    @Column(length = 500) // Access Token អាចមានប្រវែងវែង
    private String id; 

    @Column(nullable = false)
    private Date expiryTime; // កត់ត្រាទុកពេលដែល Token នេះនឹងផុតកំណត់ធម្មតា ដើម្បីងាយស្រួលសម្អាត Data ពេលក្រោយ
}