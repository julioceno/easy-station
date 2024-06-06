package com.easy_station.sso.users.services;

import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdateOwnUserDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOwnUserServiceTest {
    @InjectMocks
    UpdateOwnUserService updateOwnUserService;

    @Mock
    UpdateUserService updateUserService;

    @Mock
    FindUserByEmailService findUserByEmailService;

    UpdateOwnUserDTO updateOwnUserDTO = new UpdateOwnUserDTO("login");

    @BeforeEach
    void setUp() {
        User userMock = new User("id", null, null, null);
        when(findUserByEmailService.run("email")).thenReturn(userMock);
    }

    @Test
    @DisplayName("Should call updateUserService and invoke run method")
    void test1() {
        updateOwnUserService.run("email", updateOwnUserDTO);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(updateOwnUserDTO.email(), null, null);
        verify(updateUserService).run("id", updateUserDTO);
    }

    @Test
    @DisplayName("Should call findUserByEmailService and invoke run method")
    void test2() {
        updateOwnUserService.run("email", updateOwnUserDTO);
        verify(findUserByEmailService).run("email");
    }
}