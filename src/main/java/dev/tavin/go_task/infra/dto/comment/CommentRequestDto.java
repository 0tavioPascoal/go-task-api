package dev.tavin.go_task.infra.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank(message = "comment is required!") String comment) {
}
