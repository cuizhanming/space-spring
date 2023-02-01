package com.cuizhanming.template.identity.entity;

import java.util.UUID;

public record Identity(UUID id, String email) {
    private static Role role;
}
