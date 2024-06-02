package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindOneUserServiceTest {
    @InjectMocks
    FindOneUserService findOneUserService;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
    }

    @Test
    @DisplayName("Should call userRepository and invoke findById method")
    void test1() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        findOneUserService.run(user.getId());
        verify(userRepository).findById(user.getId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not exists")
    void test2() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(null));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            findOneUserService.run(user.getId());
        });

        assertEquals(format("Usuário de id %s não existe", user.getId()), exception.getMessage());
    }

    @Test
    @DisplayName("Should returned user instance of UserReturnDTO")
    void test3() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        UserReturnDTO response = findOneUserService.run(user.getId());

        assertThat(response).isInstanceOf(UserReturnDTO.class);
    }
}