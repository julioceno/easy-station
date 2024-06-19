package com.easy_station.management.common.services;

import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.common.grpc.SSOClientService;
import com.easy_station.management.users.database.User;
import com.easy_station.management.users.database.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.*;

@Service
public class GetCompanyIdByTokenService {
    private final Logger logger = LoggerFactory.getLogger(GetCompanyIdByTokenService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SSOClientService ssoClientService;

    public String run(String token) {
        String email = ssoClientService.validateToken(token);
        User user = getUser(email);

        logger.info("Return company id...");
        return user.getCompanyId();
    }

    private User getUser(String email) {
        logger.info(format("Getting user with email %s...", email));
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("User not found");
            return new NotFoundException(format("Usuário com email %s não existe", email));
        });

        logger.info("User got, return user...");
        return user;
    }
}
