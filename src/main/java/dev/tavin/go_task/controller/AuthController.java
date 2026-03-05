package dev.tavin.go_task.controller;

import dev.tavin.go_task.infra.dto.auth.LoginRequestDto;
import dev.tavin.go_task.infra.dto.auth.LoginResponseDto;
import dev.tavin.go_task.infra.dto.refreshToken.AuthResponse;
import dev.tavin.go_task.infra.dto.user.UserRequestDto;
import dev.tavin.go_task.infra.dto.user.UserResponseDto;
import dev.tavin.go_task.service.AuthService;
import dev.tavin.go_task.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.tavin.go_task.infra.helpers.CookiesHelper.buildCookie;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${app.security.jwt-expiration-sec}")
    private int accessTokenMaxAge;

    @Value("${app.security.refresh-expiration-sec}")
    private int refreshTokenMaxAge;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);

        ResponseCookie jwtCookie = buildCookie("access_token", response.accessToken(), accessTokenMaxAge);
        ResponseCookie refreshCookie = buildCookie("refresh_token", response.refreshToken(), refreshTokenMaxAge);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@CookieValue(name = "refresh_token") String refreshToken) {
        AuthResponse response = authService.refresh(refreshToken);

        ResponseCookie jwtCookie = buildCookie("access_token", response.acessToken(), accessTokenMaxAge);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cleanJwtCookie = buildCookie("access_token", "", 0);
        ResponseCookie cleanRefreshCookie = buildCookie("refresh_token", "", 0);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanRefreshCookie.toString())
                .build();
    }
}