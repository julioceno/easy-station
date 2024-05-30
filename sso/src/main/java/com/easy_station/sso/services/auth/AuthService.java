package com.easy_station.sso.services.auth;

import com.easy_station.sso.domain.user.dto.AuthDTO;
import com.easy_station.sso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    SignInService signInService;

    public String signIn(AuthDTO dto) {
        return signInService.run(dto);
    }
}
