package dev.tavin.go_task.infra.dto.user;

import dev.tavin.go_task.infra.entity.Comment;
import dev.tavin.go_task.infra.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(String email,
                              String username,
                              UUID id,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
