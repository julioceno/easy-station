package com.easy_station.sso.auth;

import br.com.easy_station.sso.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthGrpcController extends SSOServiceGrpc.SSOServiceImplBase {
    @Override
    public void findById(FindByIdParams request, StreamObserver<User> responseObserver) {
        User user = User.newBuilder().setId("aa").setEmail("teste").setRole(Role.ADMIN).build();
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
