package com.easy_station.sso.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class CreateTokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public SignInDTO run(User user){
        String token = this.generateRefreshToken(user);
        return SignInDTO
                .builder()
                .token(token)
                .refreshToken(token)
                .build();
    }

    private String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("easy-station-sso")
                    .withSubject(user.getLogin())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new UnauthorizedException("Não é possível autenticar o usuário.");
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
