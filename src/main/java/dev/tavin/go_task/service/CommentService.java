package dev.tavin.go_task.service;

import dev.tavin.go_task.infra.dto.comment.CommentRequestDto;
import dev.tavin.go_task.infra.dto.comment.CommentResponseDto;
import dev.tavin.go_task.infra.dto.comment.CommentUpdateDto;
import dev.tavin.go_task.infra.entity.Comment;
import dev.tavin.go_task.infra.entity.Task;
import dev.tavin.go_task.infra.repository.CommentRepository;
import dev.tavin.go_task.infra.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public CommentResponseDto save(UUID userId, UUID taskId, CommentRequestDto req) {

        Task task = taskRepository
                .findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found!"));

        Comment comment = new Comment();
        comment.setAuthor(task.getUser());
        comment.setTask(task);
        comment.setComment(req.comment());

        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getComment(),
                comment.getId(),
                comment.getTask().getId(),
                comment.getAuthor().getId()
        );
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentsForTask(UUID userId, UUID taskId, Pageable pageable) {

        Task task = taskRepository
                .findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found!"));

        return commentRepository.findAllByTask(task, pageable)
                .map(comment -> new CommentResponseDto(
                        comment.getComment(),
                        comment.getId(),
                        taskId,
                        comment.getAuthor().getId()
                ));
    }

    public void deleteComment(UUID userId, UUID commentId) {

        Comment comment = commentRepository
                .findByIdAndTaskUserId(commentId, userId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(UUID userId, UUID commentId) {

        Comment comment = commentRepository
                .findByIdAndTaskUserId(commentId, userId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        return new CommentResponseDto(
                comment.getComment(),
                comment.getId(),
                comment.getTask().getId(),
                comment.getAuthor().getId()
        );
    }

    public CommentResponseDto updateComment(UUID userId, UUID commentId, CommentUpdateDto req) {

        Comment comment = commentRepository
                .findByIdAndTaskUserId(commentId, userId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        Optional.ofNullable(req.comment())
                .ifPresent(comment::setComment);

        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getComment(),
                comment.getId(),
                comment.getTask().getId(),
                comment.getAuthor().getId()
        );
    }
}
