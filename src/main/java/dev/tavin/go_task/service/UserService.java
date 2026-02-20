package dev.tavin.go_task.service;

import dev.tavin.go_task.infra.dto.user.UserRequestDto;
import dev.tavin.go_task.infra.dto.user.UserResponseDto;
import dev.tavin.go_task.infra.entity.User;
import dev.tavin.go_task.infra.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserRequestDto req) {

        if(userRepository.findUserByEmail(req.email()).isPresent()) {
            throw new RuntimeException("User with email " + req.email() + " already exists");
        }

        User user = new User();
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setUsername(req.username());

        User saved = userRepository.save(user);

        return new UserResponseDto(saved.getEmail(), saved.getUsername(), saved.getId(), saved.getCreatedAt(), saved.getUpdatedAt());
    }

    public UserResponseDto getCurrentUser() {
        User user = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        if (user == null) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new UserResponseDto(user.getEmail(), user.getUsername(), user.getId(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public void deleteUser(UUID id) {
       userRepository.deleteById(id);
    }

}
