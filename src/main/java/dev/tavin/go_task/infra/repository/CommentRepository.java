package dev.tavin.go_task.infra.repository;

import dev.tavin.go_task.infra.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
