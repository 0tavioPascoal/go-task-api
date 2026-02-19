package dev.tavin.go_task.infra.repository;

import dev.tavin.go_task.infra.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<UserDetails>  findUserByEmail(String email);
}
