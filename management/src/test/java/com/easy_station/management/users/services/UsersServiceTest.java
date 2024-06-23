package com.easy_station.management.users.services;

import com.easy_station.management.users.dto.CreateUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @InjectMocks
    UsersService usersService;

    @Mock
    CreateUserService createUserService;

    @Test
    @DisplayName("Should call createUserService and invoke run method")
    void test1() {
        CreateUserDTO createUserDTO = new CreateUserDTO("externalUserId", "companyId");
        createUserService.run(createUserDTO);
        verify(createUserService).run(createUserDTO);
    }
}