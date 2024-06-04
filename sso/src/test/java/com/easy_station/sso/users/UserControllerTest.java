package com.easy_station.sso.users;

import com.easy_station.sso.auth.services.ValidateTokenService;
import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.dto.UpdatePasswordDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import com.easy_station.sso.users.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    ValidateTokenService validateTokenService;

    @Test
    @DisplayName("Should call userService and invoke create method")
    void test1() {
        CreateUserDTO dto = new CreateUserDTO("login", "password", UserRoleEnum.ADMIN);
        userController.create(dto);
        verify(userService).create(dto);
    }

    @Test
    @DisplayName("Should call userService and invoke findAll method")
    void test2() {
        userController.findAll();
        verify(userService).findAll();
    }

    @Test
    @DisplayName("Should call userService and invoke findOne method")
    void test3() {
        userController.findOne("id");
        verify(userService).findOne("id");
    }

    @Test
    @DisplayName("Should call userService and invoke update method")
    void test4() {
        UpdateUserDTO dto = new UpdateUserDTO("login", UserRoleEnum.ADMIN);
        userController.update("id", dto);
        verify(userService).update("id", dto);
    }

    @Test
    @DisplayName("Should call userService and invoke delete method")
    void test5() {
        userController.delete("id");
        verify(userService).delete("id");
    }

    @Test
    @DisplayName("Should call userService and invoke updatePassword method")
    void test6() {
        when(validateTokenService.run("")).thenReturn("");
        UpdatePasswordDTO dto = new UpdatePasswordDTO("old", "new");

        userController.updatePassword("", dto );
        verify(userService).updatePassword("", dto);
    }
}