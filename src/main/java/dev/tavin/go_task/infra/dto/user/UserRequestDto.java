package dev.tavin.go_task.infra.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank(message = "email is required!")
        @Email
        String email,
        @NotBlank(message = "username is required!")
        String username,
        @NotBlank(message = "password is required!")
        String password) {
}
