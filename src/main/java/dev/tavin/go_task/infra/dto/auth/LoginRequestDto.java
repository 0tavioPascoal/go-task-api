package dev.tavin.go_task.infra.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "email is required!")
        String email,
        @NotBlank(message = "password is required!")
        String password) {
}
