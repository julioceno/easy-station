package com.easy_station.sso.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class CreateTokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String run(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("easy-station-sso")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new UnauthorizedException("Não é possível autenticar o usuário.");
        }
    }

    private Instant generateExpirationDate(){
        return Instant
                .now()
                .plus(Duration.ofMinutes(30));
    }

}
