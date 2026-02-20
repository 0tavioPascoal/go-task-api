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

    public Comment(UUID id_task, String comment, Task task, User author) {
        this.id_task = id_task;
        this.comment = comment;
        this.task = task;
        this.author = author;
    }

    public Comment() {
    }

    public UUID getId_task() {
        return id_task;
    }

    public void setId_task(UUID id_task) {
        this.id_task = id_task;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id_task=" + id_task +
                ", comment='" + comment + '\'' +
                ", task=" + task +
                ", author=" + author +
                '}';
    }
}
