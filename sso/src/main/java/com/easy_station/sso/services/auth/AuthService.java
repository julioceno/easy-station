package com.easy_station.sso.services.auth;

import com.easy_station.sso.dto.user.AuthDTO;
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
