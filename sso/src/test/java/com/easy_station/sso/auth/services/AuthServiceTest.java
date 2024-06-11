package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.dto.AuthDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    SignInService signInService;

    @Mock
    ValidateTokenService validateTokenService;

    @Mock
    RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("Should call signInService and invoke run method")
    void signIn() {
        AuthDTO dto = new AuthDTO("login", "password");
        signInService.run(dto);

        verify(signInService).run(dto);
    }

    @Test
    @DisplayName("Should call validateTokenService and invoke run method")
    void validateToken() {
        validateTokenService.run("token");
        verify(validateTokenService).run("token");
    }

    @Test
    @DisplayName("Should call refreshTokenService and invoke run method")
    void refreshToken() {
        RefreshTokenDTO dto = new RefreshTokenDTO("token");
        refreshTokenService.run(dto);

        verify(refreshTokenService).run(dto);
    }
}