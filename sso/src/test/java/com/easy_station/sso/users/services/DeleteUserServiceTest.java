package com.easy_station.sso.users.services;

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
class DeleteUserServiceTest {
    @InjectMocks
    DeleteUserService deleteUserService;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
    }

    @Test
    @DisplayName("Should call userRepository and invoke deleteById method")
    void test1() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        deleteUserService.run(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    @DisplayName("Should call userRepository and invoke existsById method")
    void test2() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        deleteUserService.run(user.getId());
        verify(userRepository).existsById(user.getId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not exists")
    void test3() {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            deleteUserService.run(user.getId());
        });

        assertEquals(format("Usuário de id %s não existe", user.getId()), exception.getMessage());
    }

}