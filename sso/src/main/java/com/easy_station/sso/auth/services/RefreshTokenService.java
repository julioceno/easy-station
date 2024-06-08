package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.RefreshTokenRepository;
import com.easy_station.sso.auth.domain.RefreshToken;
import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@AllArgsConstructor
@Service
public class RefreshTokenService {
    private final CreateTokenService createTokenService;
    private final CreateRefreshTokenService createRefreshTokenService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public SignInDTO run(RefreshTokenDTO dto) {
        String refreshTokenId = dto.refreshToken();
        RefreshToken refreshToken = getRefreshToken(refreshTokenId);

        boolean isExpired = refreshTokenIsExpired(refreshToken);
        if (isExpired) {
            throw new UnauthorizedException();
        }

        User user = getUser(refreshToken.getUserId());
        String newToken = createTokenService.run(user);

        boolean isWillExpireSoon = willExpireSoon(refreshToken);
        System.out.println(isWillExpireSoon);
        if (isWillExpireSoon) {
            String newRefreshToken = createRefreshTokenService.run(user.getId());
            return toDTO(newToken, newRefreshToken);
        }

        return toDTO(newToken, refreshTokenId);
    }

    private RefreshToken getRefreshToken(String refreshTokenId) {
        RefreshToken refreshToken = this.refreshTokenRepository.findById(refreshTokenId).orElse(null);

        if (refreshToken == null) {
            throw new UnauthorizedException();
        }

        return refreshToken;
    }

    private boolean refreshTokenIsExpired(RefreshToken refreshToken) {
        Instant now = Instant.now();
        return now.isAfter(refreshToken.getExpiresIn());
    }

    private boolean willExpireSoon(RefreshToken refreshToken) {
        Instant now = Instant.now();
        Instant halfAnHourLater = now.plus(Duration.ofMinutes(30));
        return halfAnHourLater.isAfter(refreshToken.getExpiresIn());
    }

    private User getUser(String userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UnauthorizedException();
        }

        return user;
    }

    private SignInDTO toDTO(String token, String refreshToken) {
        return SignInDTO
                .builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
