package com.easy_station.management.auth.services;

import com.easy_station.management.auth.dto.SignInDTO;
import com.easy_station.management.grpc.SSOClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInService {
    @Autowired
    private SSOClientService ssoClientService;

    public void run(SignInDTO signInDTO) {

        ssoClientService.findById()
    }
}
