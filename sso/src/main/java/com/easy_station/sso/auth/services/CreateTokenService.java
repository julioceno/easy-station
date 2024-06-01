package com.easy_station.sso.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.easy_station.sso.auth.RefreshTokenRepository;
import com.easy_station.sso.auth.domain.RefreshToken;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class CreateTokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private RefreshTokenRepository repository;

    public SignInDTO run(User user){
        String token = generateToken(user);
        String refreshToken = generateRefreshToken(user.getId());

        return SignInDTO
                .builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("easy-station-sso")
                    .withSubject(user.getLogin())
                    .withExpiresAt(this.generateExpirationDate(1))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new UnauthorizedException("Não é possível autenticar o usuário.");
        }
    }

    private String generateRefreshToken(String userId) {
        RefreshToken existsUserId = repository.findByUserId(userId);
        if (existsUserId != null) {
            // TODO: add badRequestException
            throw new RuntimeException("Refresh token already exists.");
        }

        Instant expiresIn = generateExpirationDate(8);
        RefreshToken refreshToken = new RefreshToken(expiresIn, userId);
        RefreshToken refreshTokenCreated = repository.insert(refreshToken);
        return refreshTokenCreated.getId();
    }

    private Instant generateExpirationDate(int hours){
        return LocalDateTime.now()
                .plusHours(hours)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
