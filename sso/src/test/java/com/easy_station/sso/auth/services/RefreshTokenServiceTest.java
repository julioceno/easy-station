package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.RefreshTokenRepository;
import com.easy_station.sso.auth.domain.RefreshToken;
import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @InjectMocks
    RefreshTokenService refreshTokenService;

    @Mock
    CreateTokenService createTokenService;

    @Mock
    CreateRefreshTokenService createRefreshTokenService;

    @Mock
    UserRepository userRepository;

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    RefreshTokenDTO dto = new RefreshTokenDTO("refeshTokenId");
    RefreshToken refreshToken;
    User user;

    @BeforeEach
    void setUp() {
        refreshToken = new RefreshToken("id", Instant.now().plus(Duration.ofHours(8)), "userId", Instant.now());
        user = new User("id", "julio", "password", UserRoleEnum.ADMIN);
    }

    @Test
    @DisplayName("Should call refreshToken repository and invoke findById")
    void test1() {
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(user));
        refreshTokenService.run(dto);

        verify(refreshTokenRepository).findById(dto.refreshToken());
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when refresh token return null")
    void test2() {
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            refreshTokenService.run(dto);
        });

        assertEquals("Não autorizado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when refresh token is expired")
    void test3() {
        refreshToken.setExpiresIn(Instant.now().minus(Duration.ofMinutes(30)));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            refreshTokenService.run(dto);
        });

        assertEquals("Não autorizado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should call userRepository and invoke findById")
    void test4() {
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(user));
        refreshTokenService.run(dto);

        verify(userRepository).findById(refreshToken.getUserId());
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when user return null")
    void test5() {
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(null));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            refreshTokenService.run(dto);
        });

        assertEquals("Não autorizado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should call createTokenService and invoke run method with user")
    void test6() {
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(user));
        refreshTokenService.run(dto);

        verify(createTokenService).run(user);
    }

    @Test
    @DisplayName("Should call createRefreshTokenService and invoke run method with user id when token will expire soon")
    void test7() {
        refreshToken.setExpiresIn(Instant.now().plus(Duration.ofMinutes(10)));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(user));
        refreshTokenService.run(dto);

        verify(createRefreshTokenService).run(user.getId());
    }

    @Test
    @DisplayName("Should return new token and new refresh token")
    void test8() {
        refreshToken.setExpiresIn(Instant.now().plus(Duration.ofMinutes(10)));
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(user));

        when(createRefreshTokenService.run(anyString())).thenReturn("differentId");
        when(createTokenService.run(user)).thenReturn("newToken");

        SignInDTO response = refreshTokenService.run(dto);

        assertNotEquals(response.refreshToken(), refreshToken.getId());
        assertEquals(response.refreshToken(), "differentId");
        assertEquals(response.token(), "newToken");
    }

    @Test
    @DisplayName("Should return new token with old refresh token")
    void test9() {
        when(refreshTokenRepository.findById(anyString())).thenReturn(Optional.ofNullable(refreshToken));
        when(userRepository.findById(refreshToken.getUserId())).thenReturn(Optional.ofNullable(user));

        when(createTokenService.run(user)).thenReturn("newToken");

        SignInDTO response = refreshTokenService.run(dto);

        assertEquals(response.refreshToken(), dto.refreshToken());
        assertEquals(response.token(), "newToken");
    }
}