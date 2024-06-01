package com.easy_station.sso.auth.services;

import com.easy_station.sso.users.dto.AuthDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final SignInService signInService;
    private final ValidateTokenService validateTokenService;

    public String signIn(AuthDTO dto) {
        return signInService.run(dto);
    }

    public String validateToken(String token) {
        return validateTokenService.run(token);
    }
}
