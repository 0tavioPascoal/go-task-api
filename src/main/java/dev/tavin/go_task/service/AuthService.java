package dev.tavin.go_task.service;

import dev.tavin.go_task.infra.dto.auth.LoginRequestDto;
import dev.tavin.go_task.infra.dto.auth.LoginResponseDto;
import dev.tavin.go_task.infra.dto.refreshToken.AuthResponse;
import dev.tavin.go_task.infra.entity.RefreshToken;
import dev.tavin.go_task.infra.entity.User;
import dev.tavin.go_task.service.token.RefreshTokenService;
import dev.tavin.go_task.service.token.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            RefreshTokenService refreshTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    public LoginResponseDto login(LoginRequestDto request) {

        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                );

        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String accessToken = tokenService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponseDto(
                accessToken,
                refreshToken.getToken()
        );
    }

    public AuthResponse refresh(String refreshTokenValue) {

        RefreshToken oldToken = refreshTokenService.verifyExpiration(refreshTokenValue);

        User user = oldToken.getUser();

        refreshTokenService.delete(oldToken);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        String newAccessToken = tokenService.generateToken(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken.getToken()
        );
    }
}
