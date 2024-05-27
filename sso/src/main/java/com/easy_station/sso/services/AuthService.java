package com.easy_station.sso.services;

import com.easy_station.sso.dto.AuthUserDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public String createToken(AuthUserDTO dto) {
        return "Text";
    }
}
