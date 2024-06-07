package com.easy_station.sso.infa.grpc;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfiguration {
    @GrpcGlobalServerInterceptor
    public ApiKeyInterceptor apiKeyInterceptor() {
        return new ApiKeyInterceptor();
    }
}
