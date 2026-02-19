package dev.tavin.go_task.infra.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_table")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt =  LocalDateTime.now();

    private  LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<CommentModel> comments = new ArrayList<>();
}
