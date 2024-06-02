package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.RefreshTokenRepository;
import com.easy_station.sso.auth.domain.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateRefreshTokenServiceTest {
    @Mock
    RefreshTokenRepository repository;

    @Autowired
    @InjectMocks
    CreateRefreshTokenService createRefreshTokenService;

    RefreshToken refreshToken = new RefreshToken("id", Instant.now(), "userId", Instant.now());

    @Test
    @DisplayName("Should call repository and invoke findByUserId")
    void test1() {
        when(repository.findByUserId("userId")).thenReturn(null);
        when(repository.insert(any(RefreshToken.class))).thenReturn(refreshToken);

        createRefreshTokenService.run("userId");
        verify(repository).findByUserId("userId");
    }

    @Test
    @DisplayName("Should call repository and deleteById when refresh token exists in database")
    void test2() {
        when(repository.findByUserId("userId")).thenReturn(refreshToken);
        when(repository.insert(any(RefreshToken.class))).thenReturn(refreshToken);

        createRefreshTokenService.run("userId");
        verify(repository).deleteById(refreshToken.getId());
    }

    @Test
    @DisplayName("Should call repository and insert when create data in database")
    void test3() {
        when(repository.findByUserId("userId")).thenReturn(null);
        when(repository.insert(any(RefreshToken.class))).thenReturn(refreshToken);

        createRefreshTokenService.run("userId");
        verify(repository).insert(any(RefreshToken.class));
    }

    @Test
    @DisplayName("Should return refresh token id")
    void test4() {
        when(repository.findByUserId("userId")).thenReturn(null);
        when(repository.insert(any(RefreshToken.class))).thenReturn(refreshToken);

        String response = createRefreshTokenService.run("userId");
        assertEquals(response, refreshToken.getId());
    }

}