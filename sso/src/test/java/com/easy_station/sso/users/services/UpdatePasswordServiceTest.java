package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdatePasswordDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UpdatePasswordServiceTest {
    @InjectMocks
    UpdatePasswordService updatePasswordService;

    @Mock
    FindUserByEmailService findUserByEmailService;

    @Mock
    UserRepository userRepository;

    User user;
    UpdatePasswordDTO dto = new UpdatePasswordDTO("oldPassword", "newPassword");


    @BeforeAll
    static void setUpBeforeAll() {
        mockStatic(BCrypt.class);
    }


    @BeforeEach
    void setUp() {
        user = new User("id", "email", "password", UserRoleEnum.ADMIN);
    }

    @Test
    @DisplayName("Should call findUserByEmailService and invoke run method")
    void test1() {
        when(BCrypt.checkpw(anyString(), anyString())).thenReturn(true);
        when(findUserByEmailService.run(user.getEmail())).thenReturn(user);
        updatePasswordService.run(user.getEmail(), dto);

        verify(findUserByEmailService).run(user.getEmail());
    }


    @Test
    @DisplayName("Should throw UnauthorizedException when password is not equals")
    void test3() {
        when(BCrypt.checkpw(anyString(), anyString())).thenReturn(false);
        when(findUserByEmailService.run(user.getEmail())).thenReturn(user);

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            updatePasswordService.run(user.getEmail(), dto);
        });
        assertEquals("Não autorizado.", exception.getMessage());
    }

    @Test
    @DisplayName("Should call userRepository and invoke save method")
    void test4() {
        when(BCrypt.checkpw(anyString(), anyString())).thenReturn(true);
        when(findUserByEmailService.run(user.getEmail())).thenReturn(user);

        updatePasswordService.run(user.getEmail(), dto);

        User userUpdated = new User("id", "email", "newPasswordEncoded", UserRoleEnum.ADMIN);
        verify(userRepository).save(userUpdated);
    }

    @Test
    @DisplayName("Should return user instance of UserReturnDTO")
    void test5() {
        when(BCrypt.checkpw(anyString(), anyString())).thenReturn(true);
        when(findUserByEmailService.run(user.getEmail())).thenReturn(user);

        UserReturnDTO response = updatePasswordService.run(user.getEmail(), dto);

        assertThat(response).isInstanceOf(UserReturnDTO.class);
    }
}