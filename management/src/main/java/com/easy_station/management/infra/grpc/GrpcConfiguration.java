package com.easy_station.management.infra.grpc;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfiguration {
    @GrpcGlobalClientInterceptor
    public ApiKeyClientInterceptor apiKeyClientInterceptor() {
        return new ApiKeyClientInterceptor();
    }
}
