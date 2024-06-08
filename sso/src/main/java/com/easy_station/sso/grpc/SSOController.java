package com.easy_station.sso.grpc;

import br.com.easy_station.sso.*;
import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.auth.services.AuthService;
import com.easy_station.sso.users.dto.CompanyDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.services.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class SSOController extends SSOServiceGrpc.SSOServiceImplBase {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Override
    public void findById(FindUserByIdParams request, StreamObserver<User> responseObserver) {
        UserReturnDTO userEntity = userService.findOne(request.getId());
        User user = buildUser(userEntity);

        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateUserParams request, StreamObserver<User> responseObserver) {
        UpdateUserDTO userDTO = new UpdateUserDTO();
        CompanyDTO companyDTO = new CompanyDTO(
                request.getCompany().getId(),
                request.getCompany().getName()
        );

        userDTO.setCompany(companyDTO);
        UserReturnDTO userEntity = userService.update(request.getId(), userDTO);
        User user = buildUser(userEntity);

        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }

    @Override
    public void validateToken(ValidateTokenParams request, StreamObserver<ValidateTokenResponse> responseObserver) {
        String email = authService.validateToken(request.getToken());

        ValidateTokenResponse response = ValidateTokenResponse
                .newBuilder()
                .setEmail(email)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void refreshToken(RefreshTokenParams request, StreamObserver<RefreshTokenResponse> responseObserver) {
        RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO(request.getRefreshToken());
        SignInDTO signInDTO = authService.refreshToken(refreshTokenDTO);

        RefreshTokenResponse response = RefreshTokenResponse
                .newBuilder()
                .setToken(signInDTO.token())
                .setRefreshToken(signInDTO.refreshToken())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private User buildUser(UserReturnDTO userEntity) {
        CompanyDTO companyDTO = userEntity.getCompany();

        User.Builder user = User.newBuilder()
                .setId(userEntity.getId())
                .setEmail(userEntity.getEmail())
                .setRole(Role.forNumber(userEntity.getRole().ordinal()));

        if (companyDTO != null) {
            Company company = Company.newBuilder()
                    .setId(companyDTO.id())
                    .setName(companyDTO.name())
                    .build();

            user.setCompany(company);
        }

        return user.build();
    }
}
