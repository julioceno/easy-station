package com.easy_station.management.grpc;

import br.com.easy_station.sso.FindUserByIdParams;
import br.com.easy_station.sso.SSOServiceGrpc;
import br.com.easy_station.sso.User;
import com.easy_station.management.grpc.dto.UserReturnDTO;
import com.easy_station.management.infra.grpc.ApiKeyClientInterceptor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SSOClientService {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyClientInterceptor.class.getName());

    @GrpcClient("SSOService")
    private SSOServiceGrpc.SSOServiceBlockingStub ssoServiceBlockingStub;

    public UserReturnDTO findById(String id) {
        logger.info(String.format("Create call in method findOne with id %s...", id));
        FindUserByIdParams request = FindUserByIdParams.newBuilder().setId(id).build();
        User response = ssoServiceBlockingStub.findById(request);

        logger.info("User obtained, convert DTO and return user...");
        return new UserReturnDTO(response);
    }

}
