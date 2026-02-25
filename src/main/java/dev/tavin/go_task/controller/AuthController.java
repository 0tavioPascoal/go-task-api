package dev.tavin.go_task.controller;

import dev.tavin.go_task.infra.dto.auth.LoginRequestDto;
import dev.tavin.go_task.infra.dto.auth.LoginResponseDto;
import dev.tavin.go_task.infra.dto.user.UserRequestDto;
import dev.tavin.go_task.infra.dto.user.UserResponseDto;
import dev.tavin.go_task.service.AuthService;
import dev.tavin.go_task.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid  @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto req){
        //TODO add cookie for token jwt
        return new ResponseEntity<>(authService.login(req), HttpStatus.OK);
    }
}
