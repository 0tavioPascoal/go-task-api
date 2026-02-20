package dev.tavin.go_task.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.tavin.go_task.infra.dto.auth.JWTUserData;
import dev.tavin.go_task.infra.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${app.security.jwt-secret}")
    private String secret;

    private final Algorithm algorithm;

    public TokenService(@Value("${app.security.jwt-secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(User user){

        return JWT.create()
                .withClaim("userId", user.getId().toString())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try{
            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(
                    new JWTUserData(
                    UUID.fromString(decode.getClaim("userId").asString()),
                    decode.getSubject()
                    )
            );

        }  catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
