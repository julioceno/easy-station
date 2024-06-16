package com.easy_station.management.infra.http;

import com.easy_station.management.exceptions.UnauthorizedException;
import com.easy_station.management.grpc.SSOClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class.getName());

    @Autowired
    private SSOClientService ssoClientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Verifying if user is authenticated...");
        String token = recoverToken(request);

        logger.info("Validate token...");
        try {
            ssoClientService.validateToken(token);
            logger.info("User is authenticated");
            return true;
        }
        catch (RuntimeException err) {
            logger.error("Ocurred an error, user not can access route");
            throw new UnauthorizedException("Usuário não autenticado");
        }
    }

    private String recoverToken(HttpServletRequest request) {
        logger.info("Recover Token...");
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            logger.info("Token not exists");
            return null;
        };

        logger.info("Return token...");
        return authHeader.replace("Bearer ", "");
    }
}
