package com.easy_station.sso.grpc;

import br.com.easy_station.sso.*;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.users.services.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class SSOController extends SSOServiceGrpc.SSOServiceImplBase {
    @Autowired
    UserService userService;

    @Override
    public void findById(FindByIdParams request, StreamObserver<User> responseObserver) {
        UserReturnDTO userEntity = userService.findOne(request.getId());

        User user = User.newBuilder()
                .setId(userEntity.getId())
                .setEmail(userEntity.getEmail())
                .setRole(Role.forNumber(userEntity.getRole().ordinal()))
                .build();

        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateParams request, StreamObserver<User> responseObserver) {
        super.update(request, responseObserver);
    }

    @Override
    public void validateToken(ValidateTokenParams request, StreamObserver<ValidateTokenResponse> responseObserver) {
        super.validateToken(request, responseObserver);
    }
}
