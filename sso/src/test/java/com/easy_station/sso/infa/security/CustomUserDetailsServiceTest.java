package com.easy_station.sso.infa.security;

import com.easy_station.sso.exceptions.NotFoundException;
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

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
    }


    @Test
    @DisplayName("Should call userRepository and invoke findByEmail")
    void test1() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        customUserDetailsService.loadUserByUsername(user.getEmail());

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not exists")
    void test2() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(user.getEmail());
        });

        assertEquals(format("Usuário com email %s não existe.", user.getEmail()), exception.getMessage());
    }
}