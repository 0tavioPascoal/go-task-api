package dev.tavin.go_task.infra.entity;

import dev.tavin.go_task.infra.entity.enums.Status;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "task_table")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status statusTask =  Status.TODO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


}
