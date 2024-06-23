package com.easy_station.management.users.services;

import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.common.grpc.dto.UserReturnDTO;
import com.easy_station.management.common.grpc.dto.UserRoleEnum;
import com.easy_station.management.users.domain.User;
import com.easy_station.management.users.domain.UserRepository;
import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateUserServiceTest {
    @InjectMocks
    CreateUserService createUserService;

    @Mock
    UserRepository userRepository;

    @Mock
    UpdateCompanyInExternalUserService updateCompanyInExternalUserService;

    User user = new User("id", "email", "externalId", "companyId", null, null);
    CreateUserDTO createUserDTO = new CreateUserDTO("externalId", "companyId");
    UserReturnDTO userReturnDTO = new UserReturnDTO("id", "email", UserRoleEnum.USER, null);

    @BeforeEach
    void setUp() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(updateCompanyInExternalUserService.run(any(CreateUserDTO.class))).thenReturn(userReturnDTO);
    }

    @Test
    @DisplayName("Should call userRepository and invoke findByExternalId method")
    void test1() {
        createUserService.run(createUserDTO);
        verify(userRepository).findByExternalId("externalId");
    }

    @Test
    @DisplayName("Should throw BadRequestException when user already exists")
    void test2() {
        when(userRepository.findByExternalId("externalId")).thenReturn(Optional.ofNullable(user));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            createUserService.run(createUserDTO);
        });

        assertEquals("Usuário já existente.", exception.getMessage());
    }

    @Test
    @DisplayName("Should call updateCompanyInExternalUserService and invoke run method")
    void test3() {
        createUserService.run(createUserDTO);
        verify(updateCompanyInExternalUserService).run(createUserDTO);
    }

    @Test
    @DisplayName("Should call userRepository and invoke save method")
    void test4() {
        UserDTO userDTO = createUserService.run(createUserDTO);
        verify(userRepository).save(any(User.class));
        assertThat(userDTO).isInstanceOf(UserDTO.class);
    }
}