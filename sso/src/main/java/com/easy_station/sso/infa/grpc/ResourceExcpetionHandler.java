package com.easy_station.sso.infa.grpc;

import com.easy_station.sso.exceptions.BadRequestException;
import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.exceptions.UnauthorizedException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@GrpcAdvice
public class ResourceExcpetionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResourceExcpetionHandler.class.getName());

    @GrpcExceptionHandler(NotFoundException.class)
    public StatusRuntimeException notFound(NotFoundException e) {
        logger.error("Throw grpc error not found.");
        String error = "Resource not found";
        StandardError err = new StandardError(Instant.now(), error, e.getMessage());
        return Status.NOT_FOUND
                .withDescription(err.toString())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(UnauthorizedException.class)
    public StatusRuntimeException unauthorized(UnauthorizedException e) {
        String error = "Unauthorized";
        StandardError err = new StandardError(Instant.now(), error, e.getMessage());
        return Status.UNAUTHENTICATED
                .withDescription(err.toString())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(BadRequestException.class)
    public StatusRuntimeException badRequest(BadRequestException e) {
        String error = "Bad Request";
        StandardError err = new StandardError(Instant.now(), error, e.getMessage());
        return Status.FAILED_PRECONDITION
                .withDescription(err.toString())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(RuntimeException.class)
    public StatusRuntimeException internalServerError() {
        String error = "Internal Server Error";
        String message = "Ocorreu um erro inesperado, entre em contato com o suporte.";
        StandardError err = new StandardError(Instant.now(), error, message);
        return Status.UNKNOWN
                .withDescription(err.toString())
                .asRuntimeException();
    }
}
