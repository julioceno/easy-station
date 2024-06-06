package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {
    @InjectMocks
    UpdateUserService updateUserService;

    @Mock
    UserRepository userRepository;

    String id = "id";
    UpdateUserDTO dto = new UpdateUserDTO("email", UserRoleEnum.ADMIN, null);
    User user;

    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
    }

    @Test
    @DisplayName("Should call userRepository and invoke findById")
    void test1() {
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        updateUserService.run(id, dto);
        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not exists")
    void test2() {
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateUserService.run(id, dto);
        });

        assertEquals(format("Usuário de id %s não existe", id), exception.getMessage());
    }

    @Test
    @DisplayName("Should call userRepository and invoke save method")
    void test3() {
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        updateUserService.run(id, dto);

        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should call save method with prop email and rol updated")
    void test4() {
        User newUser = new User("id", "newEmail", "password", UserRoleEnum.USER);
        UpdateUserDTO dto = new UpdateUserDTO("newEmail", UserRoleEnum.USER, null);

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        updateUserService.run(id, dto);

        verify(userRepository).save(newUser);
    }

    @Test
    @DisplayName("Should return user instance of UserReturnDTO")
    void test5() {
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        UserReturnDTO response = updateUserService.run(id, dto);

        assertThat(response).isInstanceOf(UserReturnDTO.class);
    }
}