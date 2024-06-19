package com.easy_station.management.auth.services;

import com.easy_station.management.auth.dto.AuthDTO;
import com.easy_station.management.auth.dto.SignInDTO;
import com.easy_station.management.common.grpc.SSOClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInService {
    @Autowired
    private SSOClientService ssoClientService;

    public void run(AuthDTO authDTO) {
        SignInDTO response = ssoClientService.login(authDTO);


    }
}
