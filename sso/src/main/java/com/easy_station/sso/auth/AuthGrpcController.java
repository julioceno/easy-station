package com.easy_station.sso.auth;

import br.com.julioceno.v1.user.FindByIdParams;
import br.com.julioceno.v1.user.User;
import br.com.julioceno.v1.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthGrpcController extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void findById(FindByIdParams request, StreamObserver<User> responseObserver) {
        User user = User.newBuilder().setId("aa").setEmail("teste").setRole("roe").build();
        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }
}
