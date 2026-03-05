package dev.tavin.go_task.service.token;

import dev.tavin.go_task.infra.entity.RefreshToken;
import dev.tavin.go_task.infra.entity.User;
import dev.tavin.go_task.infra.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    @Value("${app.security.refresh-expiration-sec}")
    private long refreshExpirationSec;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusSeconds(refreshExpirationSec));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken verifyExpiration(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(rt -> {
                    if (rt.getExpiresAt().isBefore(Instant.now())) {
                        refreshTokenRepository.delete(rt);
                        throw new RuntimeException("Refresh token expirado. Por favor, faça login novamente.");
                    }
                    return rt;
                })
                .orElseThrow(() -> new RuntimeException("Refresh token inválido ou não encontrado."));
    }

    @Transactional
    public void delete(RefreshToken oldToken) {
        refreshTokenRepository.delete(oldToken);
    }
}