package com.easy_station.management.common.grpc;

import br.com.easy_station.sso.*;
import com.easy_station.management.common.dto.SubjectDTO;
import com.easy_station.management.common.exceptions.UnauthorizedException;
import com.easy_station.management.common.grpc.dto.UserReturnDTO;
import com.easy_station.management.infra.grpc.ApiKeyClientInterceptor;
import com.easy_station.management.users.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

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

    public UserReturnDTO update(UserDTO userDTO) {
        logger.info(format("Create call in method update with user %s...", userDTO.toString()));
        Company company = Company
                .newBuilder()
                .setId(userDTO.getCompany().getId())
                .setName(userDTO.getCompany().getName())
                .build();

        UpdateUserParams request = UpdateUserParams
                .newBuilder()
                .setId(userDTO.getExternalId())
                .setCompany(company)
                .build();

        User response = ssoServiceBlockingStub.update(request);

        logger.info("User updated");
        return new UserReturnDTO(response);
    }

    public SubjectDTO validateToken(String token) {
        logger.info("Create call in method validateToken");
        ValidateTokenParams request = ValidateTokenParams
                .newBuilder()
                .setToken(token)
                .build();

        ValidateTokenResponse response = ssoServiceBlockingStub.validateToken(request);
        logger.info("Token validated");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getSubject(), SubjectDTO.class);
        } catch (JsonProcessingException e) {
            logger.error("Error in json");
            throw new UnauthorizedException();
        }
    }

}
