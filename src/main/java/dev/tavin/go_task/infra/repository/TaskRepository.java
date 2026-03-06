package dev.tavin.go_task.infra.repository;

import dev.tavin.go_task.infra.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllTaskByUserId(UUID id);

    Page<Task> findAllTaskByUserId(UUID userId, Pageable pageable);

    Optional<Task> findByIdAndUserId(UUID taskId, UUID userId);
}
