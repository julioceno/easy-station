package com.easy_station.management.users;

import com.easy_station.management.users.domain.User;
import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import com.easy_station.management.users.services.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {
    @InjectMocks
    UsersController usersController;

    @Mock
    UsersService usersService;

    @Test
    @DisplayName("Should call usersService and invoke run method")
    void test1() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        UserDTO user = new UserDTO("id", "email", "companyId", "externalId", null);

        CreateUserDTO createUserDTO = new CreateUserDTO("externalUserId", "companyId");
        when(usersService.create(createUserDTO)).thenReturn(user);

        usersController.create(createUserDTO);

        verify(usersService).create(createUserDTO);
    }
}