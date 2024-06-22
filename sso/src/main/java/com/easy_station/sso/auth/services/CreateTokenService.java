package com.easy_station.sso.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.SubjectDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class CreateTokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String run(User user){
        SubjectDTO subjectDTO = new SubjectDTO(user.getEmail(), user.getRole());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String subjectJson = objectMapper.writeValueAsString(subjectDTO);

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("easy-station-sso")
                    .withSubject(subjectJson)
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException | JsonProcessingException exception){
            throw new UnauthorizedException("Não é possível autenticar o usuário.");
        }
    }

    private Instant generateExpirationDate(){
        return Instant
                .now()
                .plus(Duration.ofHours(30)); // TODO Diminuir o tempo
    }

}
