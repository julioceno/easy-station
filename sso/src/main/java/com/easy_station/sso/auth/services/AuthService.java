package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.users.dto.AuthDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final SignInService signInService;
    private final ValidateTokenService validateTokenService;
    private final RefreshTokenService refreshTokenService;

    public SignInDTO signIn(AuthDTO dto) {
        return signInService.run(dto);
    }

    public String validateToken(String token) {
        return validateTokenService.run(token);
    }

    public SignInDTO refreshToken(RefreshTokenDTO dto) {
        return refreshTokenService.run(dto);
    }
}
