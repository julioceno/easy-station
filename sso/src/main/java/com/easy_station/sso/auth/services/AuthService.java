package com.easy_station.sso.auth.services;

import com.easy_station.sso.users.dto.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    SignInService signInService;

    public String signIn(AuthDTO dto) {
        return signInService.run(dto);
    }
}
