package com.phaiecobyte.pos.backend.identity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterReq {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String roleName;
}
