package com.easy_station.management.infra.grpc;


import io.grpc.*;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ApiKeyClientInterceptor implements ClientInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyClientInterceptor.class.getName());
    private static final Metadata.Key<String> API_KEY_HEADER = Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER);

    @Value("${grpc.apikey}")
    private String apiKey;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next
    ) {

        return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(API_KEY_HEADER, apiKey);
                super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        logger.info("header received from server:" + headers.toString());
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
};
