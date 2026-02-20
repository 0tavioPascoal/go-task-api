package dev.tavin.go_task.infra.dto.auth;

import java.util.UUID;

public record JWTUserData(UUID id, String email) {

    @Override
    public String email() {
        return email;
    }

    @Override
    public UUID id() {
        return id;
    }
}
