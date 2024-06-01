package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.RefreshTokenRepository;
import com.easy_station.sso.auth.domain.RefreshToken;
import com.easy_station.sso.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class CreateRefreshTokenService {
    @Autowired
    private RefreshTokenRepository repository;

    public String run(String userId) {
        RefreshToken existsUserId = repository.findByUserId(userId);
        if (existsUserId != null) {
            repository.deleteById(existsUserId.getId());
        }

        RefreshToken refreshTokenCreated = createRefreshToken(userId);
        return refreshTokenCreated.getId();
    }

    private RefreshToken createRefreshToken(String userId) {
        Instant expiresIn = generateExpirationDate();
        RefreshToken refreshToken = new RefreshToken(expiresIn, userId);
        return repository.insert(refreshToken);
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
