package dev.tavin.go_task.infra.dto.comment;

import jakarta.validation.constraints.NotNull;

public record CommentUpdateDto(
        @NotNull(message = "Comment is required!")
        String comment) {
}
