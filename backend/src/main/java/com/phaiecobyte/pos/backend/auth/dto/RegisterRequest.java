package com.phaiecobyte.pos.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;
    // អ្នកអាចបន្ថែម Field ផ្សេងៗទៀតបើសិនជាត្រូវការ (ឧ. email, phone)

    // បញ្ជាក់ Role ដែលចង់ចុះឈ្មោះ (បើអ្នកចង់អោយ Admin ជាអ្នកជ្រើសរើសពេលបង្កើត)
    private String roleName;
}
