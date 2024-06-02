package com.easy_station.sso.auth;

import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.services.AuthService;
import com.easy_station.sso.users.dto.AuthDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @InjectMocks
    AuthController controller;

    @Mock
    AuthService authService;

    @Test
    @DisplayName("Should call authService and invoke signIn method")
    void auth1() {
        AuthDTO dto = new AuthDTO("login", "password");
        controller.auth(dto);
        verify(authService).signIn(dto);
    }

    @Test
    @DisplayName("Should call authService and invoke refreshToken method")
    void test2() {
        RefreshTokenDTO dto = new RefreshTokenDTO("refreshToken");
        controller.refreshToken(dto);
        verify(authService).refreshToken(dto);
    }
}