package dev.tavin.go_task.infra.repository;

import dev.tavin.go_task.infra.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
