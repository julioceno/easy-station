package com.easy_station.sso.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.easy_station.sso.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateTokenServiceTest {
    @InjectMocks
    private ValidateTokenService validateTokenService;

    @BeforeAll
    static void setUpBeforeAll() {
        mockStatic(JWT.class);
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(validateTokenService, "secret", "jwtSecret");
    }

    @Test
    @DisplayName("Should return subject token")
    void test1() {
        JWTVerifier.BaseVerification jwtBaseVerification = mock(JWTVerifier.BaseVerification.class);
        JWTVerifier verifier = mock(JWTVerifier.class);
        DecodedJWT decodedJWT = mock(DecodedJWT.class);

        when(jwtBaseVerification.withIssuer(anyString())).thenReturn(jwtBaseVerification);
        when(jwtBaseVerification.build()).thenReturn(verifier);
        when(verifier.verify(anyString())).thenReturn(decodedJWT);
        when(decodedJWT.getSubject()).thenReturn("subject-jwt");

        when(JWT.require(any(Algorithm.class))).thenReturn(jwtBaseVerification);

        String response = validateTokenService.run("token");

        assertEquals(response, "subject-jwt");
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when token decode fail")
    void test2() {
        when(JWT.require(any(Algorithm.class))).thenThrow(new JWTVerificationException("error"));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            validateTokenService.run("token");
        });

        assertEquals("Usuário não está autenticado.", exception.getMessage());
    }
}