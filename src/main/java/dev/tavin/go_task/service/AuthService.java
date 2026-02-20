package dev.tavin.go_task.service;

import dev.tavin.go_task.infra.dto.auth.LoginRequestDto;
import dev.tavin.go_task.infra.dto.auth.LoginResponseDto;
import dev.tavin.go_task.infra.entity.User;
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

    public AuthService( AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponseDto login(LoginRequestDto request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user =  (User) authentication.getPrincipal();
        if (user == null) {
            throw new BadCredentialsException("Invalid email or password");
        }
        String token = tokenService.generateToken(user);
        return new LoginResponseDto(token);
    }
}
