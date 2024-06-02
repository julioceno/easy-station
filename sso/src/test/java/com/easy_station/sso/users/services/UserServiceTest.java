package com.easy_station.sso.users.services;

import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    CreateUserService createUserService;

    @Mock
    FindAllUsersService findAllUsersService;

    @Mock
    FindOneUserService findOneUserService;

    @Mock
    UpdateUserService updateUserService;

    @Mock
    DeleteUserService deleteUserService;

    @Test
    @DisplayName("Should call createUserService and invoke run method")
    void test1() {
        CreateUserDTO dto = new CreateUserDTO("login", "password", UserRoleEnum.ADMIN);
        userService.create(dto);
        verify(createUserService).run(dto);
    }

    @Test
    @DisplayName("Should call findAllUsersService and invoke run method")
    void test2() {
        userService.findAll();
        verify(findAllUsersService).run();
    }

    @Test
    @DisplayName("Should call findOneUserService and invoke run method")
    void test3() {
        userService.findOne("id");
        verify(findOneUserService).run("id");
    }

    @Test
    @DisplayName("Should call updateUserService and invoke run method")
    void test4() {
        UpdateUserDTO dto = new UpdateUserDTO("login", UserRoleEnum.ADMIN);
        userService.update("id", dto);
        verify(updateUserService).run("id", dto);
    }

    @Test
    @DisplayName("Should call deleteUserService and invoke run method")
    void test5() {
        userService.delete("id");
        verify(deleteUserService).run("id");
    }
}