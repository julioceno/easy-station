package com.easy_station.sso.users.services;

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

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FindAllUsersServiceTest {
    @InjectMocks
    FindAllUsersService findAllUsersService;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
        when(userRepository.findAll()).thenReturn(List.of(user));
    }

    @Test
    @DisplayName("Should call userRepository and invoke findAll")
    void test1() {
        findAllUsersService.run();
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should mapping users to UserReturnDTO")
    void test2() {
        List<UserReturnDTO> users = findAllUsersService.run();
        assertThat(users).isInstanceOf(List.class);
        users.forEach(user -> {
            assertThat(user).isInstanceOf(UserReturnDTO.class);
        });
    }
}