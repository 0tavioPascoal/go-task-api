package dev.tavin.go_task.infra.dto.auth;

public record LoginResponseDto(
            String accessToken,
            String refreshToken){
}
