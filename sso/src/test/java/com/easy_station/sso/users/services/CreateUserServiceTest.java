package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.BadRequestException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;

import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {
    @InjectMocks
    CreateUserService createUserService;

    @Mock
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder;

    User user;
    CreateUserDTO dto = new CreateUserDTO("email", "password", UserRoleEnum.ADMIN);

    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("Should call userRepository and invoke findByEmail")
    void test1() {
        when(userRepository.findByEmail(dto.email())).thenReturn(null);

        createUserService.run(dto);
        verify(userRepository).findByEmail(dto.email());
    }

    @Test
    @DisplayName("Should throw BadRequestException when user not exists")
    void test2() {
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.ofNullable(user));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            createUserService.run(dto);
        });

        assertEquals(format("Email %s já esta em uso", dto.email()), exception.getMessage());
    }

    @Test
    @DisplayName("Should invoke userRepository and invoke save method")
    void test3() {
        when(userRepository.findByEmail(dto.email())).thenReturn(null);
        createUserService.run(dto);

        verify(userRepository).save(any(User.class));
        verify(userRepository).save(argThat(newUser -> {
            return passwordEncoder.matches("password", newUser.getPassword());
        }));
    }

    @Test
    @DisplayName("Should return user instance of UserReturnDTO")
    void test4() {
        when(userRepository.findByEmail(dto.email())).thenReturn(null);
        UserReturnDTO response = createUserService.run(dto);
        assertThat(response).isInstanceOf(UserReturnDTO.class);
    }
}