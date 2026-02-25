package dev.tavin.go_task.infra.dto.task;

import dev.tavin.go_task.infra.entity.enums.Status;
import jakarta.validation.constraints.NotNull;

public record TaskUpdateStatusDto( @NotNull(message = "Status is required!") Status status) {
}
