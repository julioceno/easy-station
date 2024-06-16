package com.easy_station.management.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class.getName());

    public static String recoverToken(String bearerToken) {
        logger.info("Validate Token...");
        if (bearerToken == null) {
            logger.info("Token not exists");
            return null;
        };

        logger.info("Return token...");
        return bearerToken.replace("Bearer ", "");
    }
}
