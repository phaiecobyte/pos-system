package com.phaiecobyte.pos.backend.identity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class AssignRoleReq {
    private Set<UUID> roleIds;
}
