package com.easy_station.management.grpc;

import br.com.easy_station.sso.FindUserByIdParams;
import br.com.easy_station.sso.SSOServiceGrpc;
import br.com.easy_station.sso.User;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class SSOClient {
    @GrpcClient("SSOService")
    private SSOServiceGrpc.SSOServiceBlockingStub ssoServiceBlockingStub;

    public User findById(String id) {
        FindUserByIdParams request = FindUserByIdParams.newBuilder().setId(id).build();
        return ssoServiceBlockingStub.findById(request);
    }

}
