package dev.tavin.go_task.infra.repository;

import dev.tavin.go_task.infra.entity.Comment;
import dev.tavin.go_task.infra.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Transactional(readOnly = true)
    List<Comment> findAllByTask(Task task);

    Page<Comment> findAllByTask(Task task, Pageable pageable);

    @Transactional(readOnly = true)
    Optional<Comment> findByIdAndTaskUserId(UUID id, UUID userId);
}
