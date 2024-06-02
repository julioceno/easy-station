package com.easy_station.sso.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTokenServiceTest {
    @InjectMocks
    private CreateTokenService createTokenService;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(createTokenService, "secret", "jwtSecret");
    }

    @Test
    @DisplayName("Should return token when user is valid")
    void test1() {
        when(user.getLogin()).thenReturn("testUser");

        JWTCreator.Builder jwtBuilder = mock(JWTCreator.Builder.class);
        when(jwtBuilder.withIssuer(anyString())).thenReturn(jwtBuilder);
        when(jwtBuilder.withSubject(anyString())).thenReturn(jwtBuilder);
        when(jwtBuilder.withExpiresAt((Instant) any())).thenReturn(jwtBuilder);
        when(jwtBuilder.sign(any(Algorithm.class))).thenReturn("token-jwt");

        mockStatic(JWT.class);
        when(JWT.create()).thenReturn(jwtBuilder);

        String token = createTokenService.run(user);

        assertEquals("token-jwt", token);
    }

    @Test
    @DisplayName("Should throw error when ocurred error in create token")
    void test2() {
        mockStatic(JWT.class);
        when(JWT.create()).thenThrow(new JWTCreationException("error", null));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            createTokenService.run(user);
        });

        assertEquals("Não é possível autenticar o usuário.", exception.getMessage());
    }
}