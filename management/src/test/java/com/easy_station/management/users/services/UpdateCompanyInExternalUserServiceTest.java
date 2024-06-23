package com.easy_station.management.users.services;

import com.easy_station.management.common.grpc.SSOClientService;
import com.easy_station.management.common.grpc.dto.UserReturnDTO;
import com.easy_station.management.common.grpc.dto.UserRoleEnum;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.services.FindOneCompanyService;
import com.easy_station.management.users.domain.User;
import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateCompanyInExternalUserServiceTest {
    @InjectMocks
    UpdateCompanyInExternalUserService updateCompanyInExternalUserService;

    @Mock
    SSOClientService ssoClientService;

    @Mock
    FindOneCompanyService findOneCompanyService;

    CreateUserDTO createUserDTO = new CreateUserDTO("externalId", "companyId");
    CompanyDTO companyDTO = new CompanyDTO("id", "name", 10.0);
    UserReturnDTO userReturnDTO = new UserReturnDTO("id", "email", UserRoleEnum.USER, null);

    @BeforeEach
    void setUp() {
        when(findOneCompanyService.run(createUserDTO.companyId())).thenReturn(companyDTO);
        when(ssoClientService.update(any(UserDTO.class))).thenReturn(userReturnDTO);
    }

    @Test
    @DisplayName("Should findOneCompanyService and invoke run method")
    void test1() {
        updateCompanyInExternalUserService.run(createUserDTO);
        verify(findOneCompanyService).run(createUserDTO.companyId());
    }

    @Test
    @DisplayName("Should call ssoClientService and call update method")
    void test2() {
        UserReturnDTO userReturnDTO = updateCompanyInExternalUserService.run(createUserDTO);
        verify(ssoClientService).update(any(UserDTO.class));
        assertThat(userReturnDTO).isInstanceOf(UserReturnDTO.class);
    }
}