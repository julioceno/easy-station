package com.easy_station.management.infra.http.interceptors;

import com.easy_station.management.common.services.GetCompanyIdByTokenService;
import com.easy_station.management.common.utils.Utils;
import com.easy_station.management.common.exceptions.UnauthorizedException;
import com.easy_station.management.common.grpc.SSOClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static java.lang.String.*;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class.getName());

    @Autowired
    private SSOClientService ssoClientService;

    @Autowired
    private GetCompanyIdByTokenService getCompanyIdByTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Verifying if user is authenticated...");
        String bearerToken = request.getHeader("Authorization");
        String token = Utils.recoverToken(bearerToken);

        logger.info("Validate token...");

        try {
            ssoClientService.validateToken(token);
            logger.info("User is authenticated");
            setCompanyIdInRequest(token, request);

            logger.info("Authorized user");
            return true;
        }
        catch (RuntimeException err) {
            logger.error("Ocurred an error, user not can access route");
            throw new UnauthorizedException("Usuário não autenticado");
        }
    }

    private void setCompanyIdInRequest(String token, HttpServletRequest request) {
        logger.info("calling getCompanyIdByTokenService and call run method");
        String companyId = getCompanyIdByTokenService.run(token);
        logger.info(format("CompanyId with id %s got, set in attribute request", companyId));
        request.setAttribute("companyId", companyId);
    }
}
