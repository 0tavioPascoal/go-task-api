package dev.tavin.go_task.infra.entity;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "comment_table")
public class Comment {

    @Id
    @GeneratedValue
    private UUID id_task;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
