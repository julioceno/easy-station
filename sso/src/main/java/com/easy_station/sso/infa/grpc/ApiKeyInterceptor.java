package com.easy_station.sso.infa.grpc;

import com.easy_station.sso.exceptions.UnauthorizedException;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public class ApiKeyInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyInterceptor.class.getName());
    private static final Metadata.Key<String> API_KEY_HEADER = Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER);

    @Value("${grpc.apikey}")
    private String apiKey;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler
    ) {
        logger.info("Header received from client." );
        String apiKeyReceived = metadata.get(API_KEY_HEADER);
        if (!Objects.equals(apiKeyReceived, apiKey)) {
            logger.error("Api key is invalid.");
            throw new UnauthorizedException("A api key é inválida.");
        }

        logger.info("Api key is valid.");
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
