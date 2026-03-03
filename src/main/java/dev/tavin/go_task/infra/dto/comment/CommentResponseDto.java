package dev.tavin.go_task.infra.dto.comment;

import java.util.UUID;

public record CommentResponseDto(String comment, UUID id, UUID task_id, UUID user_id) {
}
