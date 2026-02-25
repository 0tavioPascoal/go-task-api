package dev.tavin.go_task.infra.dto.task;

import dev.tavin.go_task.infra.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDto(
        String title,
        String description,
        Status status,
        UUID taskId) {
}
