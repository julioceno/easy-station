package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.RefreshTokenRepository;
import com.easy_station.sso.auth.domain.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

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
        return Instant
                .now()
                .plus(Duration.ofMinutes(30));
    }
}
