package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.users.dto.AuthDTO;
import com.easy_station.sso.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SignInService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CreateTokenService createTokenService;

    public SignInDTO run(AuthDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return createTokenService.run((User) auth.getPrincipal());
    }
}
