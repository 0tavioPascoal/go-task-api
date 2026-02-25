package dev.tavin.go_task.infra.dto.task;

import dev.tavin.go_task.infra.dto.user.UserRequestDto;

public record TaskRequestDto(String title, String description , UserRequestDto userRequestDto) {
}
