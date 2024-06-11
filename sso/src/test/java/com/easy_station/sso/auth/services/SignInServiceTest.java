package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.auth.dto.AuthDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignInServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CreateTokenService createTokenService;

    @Mock
    private CreateRefreshTokenService createRefreshTokenService;

    @Autowired
    @InjectMocks
    SignInService signInService;

    @Test
    @DisplayName("Should return token and refresh token")
    void test1() {
        AuthDTO authDTO = new AuthDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());

        User user = mock(User.class);
        when(user.getId()).thenReturn("");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
        when(createTokenService.run(user)).thenReturn("test-token");
        Mockito.when(createRefreshTokenService.run(user.getId())).thenReturn("test-refresh-token");

        SignInDTO signInDTO = signInService.run(authDTO);

        assertEquals("test-token", signInDTO.token());
        assertEquals("test-refresh-token", signInDTO.refreshToken());
    }

    @Test
    @DisplayName("Should call authenticationManager and invoke authenticate method")
    void test2() {
        AuthDTO authDTO = new AuthDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());

        User user = mock(User.class);
        Mockito.when(user.getId()).thenReturn("");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
        when(createTokenService.run(user)).thenReturn("test-token");
        Mockito.when(createRefreshTokenService.run(user.getId())).thenReturn("test-refresh-token");

        signInService.run(authDTO);

        verify(authenticationManager).authenticate(usernamePassword);
    }

    @Test
    @DisplayName("Should call createTokenService and invoke run method")
    void test3() {
        AuthDTO authDTO = new AuthDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());

        User user = mock(User.class);
        Mockito.when(user.getId()).thenReturn("");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
        when(createTokenService.run(user)).thenReturn("test-token");
        Mockito.when(createRefreshTokenService.run(user.getId())).thenReturn("test-refresh-token");

        signInService.run(authDTO);

        verify(createTokenService).run(user);
    }

    @Test
    @DisplayName("Should call createTokenService and invoke run method")
    void test4() {
        AuthDTO authDTO = new AuthDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());

        User user = mock(User.class);
        Mockito.when(user.getId()).thenReturn("");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
        when(createTokenService.run(user)).thenReturn("test-token");
        Mockito.when(createRefreshTokenService.run(user.getId())).thenReturn("test-refresh-token");

        signInService.run(authDTO);

        verify(createRefreshTokenService).run(user.getId());
    }
}