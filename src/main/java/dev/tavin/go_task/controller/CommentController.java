package dev.tavin.go_task.controller;

import dev.tavin.go_task.infra.dto.comment.CommentRequestDto;
import dev.tavin.go_task.infra.dto.comment.CommentResponseDto;
import dev.tavin.go_task.infra.dto.comment.CommentUpdateDto;
import dev.tavin.go_task.infra.entity.Comment;
import dev.tavin.go_task.infra.entity.User;
import dev.tavin.go_task.service.CommentService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable UUID taskId,
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(
                commentService.save(user.getId(), taskId, commentRequestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<Page<CommentResponseDto>> getAllCommentsForTask(
            @AuthenticationPrincipal User user,
            @PathVariable UUID taskId,
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(
                commentService.getCommentsForTask(user.getId(), taskId, pageable),
                HttpStatus.OK
        );
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID commentId
    ) {
        return new ResponseEntity<>(
                commentService.findById(user.getId(), commentId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal User user,
            @PathVariable UUID commentId
    ) {
        commentService.deleteComment(user.getId(), commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @AuthenticationPrincipal User user,
            @PathVariable UUID commentId,
            @Valid @RequestBody CommentUpdateDto commentUpdateDto
    ) {
        return new ResponseEntity<>(
                commentService.updateComment(user.getId(), commentId, commentUpdateDto),
                HttpStatus.OK
        );
    }
}
