package com.easy_station.sso.users.services;

import com.easy_station.sso.users.dto.UpdateOwnUserDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
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

    UpdateOwnUserDTO updateOwnUserDTO = new UpdateOwnUserDTO("login");

    @Test
    @DisplayName("Should call updateUserService and call run method")
    void test1() {
        updateOwnUserService.run("id", updateOwnUserDTO);
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(updateOwnUserDTO.email(), null, null);
        verify(updateUserService).run("id", updateUserDTO);
    }
}