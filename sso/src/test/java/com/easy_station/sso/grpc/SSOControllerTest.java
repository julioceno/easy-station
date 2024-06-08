package com.easy_station.sso.grpc;

import br.com.easy_station.sso.*;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.auth.services.AuthService;
import com.easy_station.sso.users.dto.CompanyDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.dto.UserRoleEnum;
import com.easy_station.sso.users.services.UserService;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SSOControllerTest {
    @InjectMocks
    private SSOController ssoController;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    CompanyDTO companyDTO = new CompanyDTO("id", "name");
    com.easy_station.sso.users.domain.User userMock = new com.easy_station.sso.users.domain.User("id", "email@easystation.com.br", "password", UserRoleEnum.ADMIN, null);
    UserReturnDTO userReturnDTO = new UserReturnDTO(userMock);

    StreamObserver responseObserver;
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    @BeforeEach
    void setUp() {
        responseObserver = mock(StreamObserver.class);
    }

    @Test
    @DisplayName("Should call findById and build user")
    void test1() {
        when(userService.findOne(any())).thenReturn(userReturnDTO);
        FindUserByIdParams request = FindUserByIdParams
                .newBuilder()
                .setId("")
                .build();

        ssoController.findById(request, responseObserver);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(responseObserver).onNext(userCaptor.capture());
        verify(responseObserver).onCompleted();

        User response = userCaptor.getValue();
        assertEquals("id", response.getId());
        assertEquals("email@easystation.com.br", response.getEmail());
    }

    @Test
    @DisplayName("Should call findById and build user with company")
    void test2() {
        com.easy_station.sso.users.domain.User userMock = new com.easy_station.sso.users.domain.User("id", "email@easystation.com.br", "password", UserRoleEnum.ADMIN, companyDTO);

        UserReturnDTO userReturnDTO = new UserReturnDTO(userMock);
        when(userService.findOne(any())).thenReturn(userReturnDTO);
        FindUserByIdParams request = FindUserByIdParams
                .newBuilder()
                .setId("")
                .build();

        ssoController.findById(request, responseObserver);


        verify(responseObserver).onNext(userCaptor.capture());
        verify(responseObserver).onCompleted();

        User response = userCaptor.getValue();
        assertEquals("id", response.getId());
        assertEquals("email@easystation.com.br", response.getEmail());
        assertEquals("id", companyDTO.id());
        assertEquals("name", companyDTO.name());
    }

    @Test
    @DisplayName("Should call update and build user")
    void test3() {
        Company company = Company
                .newBuilder()
                .setId(companyDTO.id())
                .setName(companyDTO.name())
                .build();

        UpdateUserParams request = UpdateUserParams
                .newBuilder()
                .setId(userMock.getId())
                .setCompany(company)
                .build();

        when(userService.update(any(), any())).thenReturn(userReturnDTO);

        ssoController.update(request, responseObserver);

        verify(responseObserver).onNext(userCaptor.capture());
        verify(responseObserver).onCompleted();

        User response = userCaptor.getValue();
        assertEquals("id", response.getId());
        assertEquals("email@easystation.com.br", response.getEmail());
    }

    @Test
    @DisplayName("Should call validateToken and build decoded token")
    void test4() {
        ArgumentCaptor<ValidateTokenResponse> validateTokenCaptor = ArgumentCaptor.forClass(ValidateTokenResponse.class);
        ValidateTokenParams request = ValidateTokenParams
                .newBuilder()
                .setToken("token")
                .build();

        when(authService.validateToken(any())).thenReturn(userMock.getEmail());

        ssoController.validateToken(request, responseObserver);

        verify(responseObserver).onNext(validateTokenCaptor.capture());
        verify(responseObserver).onCompleted();

        ValidateTokenResponse response = validateTokenCaptor.getValue();
        assertEquals("email@easystation.com.br", response.getEmail());
    }

    @Test
    @DisplayName("Should call refreshToken and build token and refresh token")
    void test5() {
        ArgumentCaptor<RefreshTokenResponse> validateTokenCaptor = ArgumentCaptor.forClass(RefreshTokenResponse.class);
        RefreshTokenParams request = RefreshTokenParams
                .newBuilder()
                .setRefreshToken("refreshToken")
                .build();
        SignInDTO signInDTO = new SignInDTO("token", "refreshToken");

        when(authService.refreshToken(any())).thenReturn(signInDTO);

        ssoController.refreshToken(request, responseObserver);

        verify(responseObserver).onNext(validateTokenCaptor.capture());
        verify(responseObserver).onCompleted();

        RefreshTokenResponse response = validateTokenCaptor.getValue();
        assertEquals("token", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }
}