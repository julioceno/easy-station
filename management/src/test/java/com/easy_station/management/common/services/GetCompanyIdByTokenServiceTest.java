package com.easy_station.management.common.services;

import com.easy_station.management.common.dto.SubjectDTO;
import com.easy_station.management.common.enums.UserRoleEnum;
import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.common.grpc.SSOClientService;
import com.easy_station.management.users.domain.User;
import com.easy_station.management.users.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCompanyIdByTokenServiceTest {
    @InjectMocks
    GetCompanyIdByTokenService getCompanyIdByTokenService;

    @Mock
    UserRepository userRepository;

    @Mock
    SSOClientService ssoClientService;

    SubjectDTO subjectDTO = new SubjectDTO("email", UserRoleEnum.USER);
    User user = new User("id", "email", "externalId", "companyId", null, null);

    @BeforeEach
    void setUp() {
        when(ssoClientService.validateToken("token")).thenReturn(subjectDTO);
        when(userRepository.findByEmail("email")).thenReturn(Optional.ofNullable(user));
    }

    @Test
    @DisplayName("Should call ssoClientService and invoke validateToken")
    void test1() {
        getCompanyIdByTokenService.run("token");
        verify(ssoClientService).validateToken("token");
    }

    @Test
    @DisplayName("Should call userRepository and invoke findByEmail")
    void test2() {
        getCompanyIdByTokenService.run("token");
        verify(userRepository).findByEmail("email");
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not exists")
    void test3() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            getCompanyIdByTokenService.run("token");
        });

        assertEquals(format("Usuário com email %s não existe", "email"), exception.getMessage());
    }
}