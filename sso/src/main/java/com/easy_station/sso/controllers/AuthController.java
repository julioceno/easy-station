package com.easy_station.sso.controllers;

import com.easy_station.sso.domain.user.dto.AuthDTO;
import com.easy_station.sso.domain.user.dto.LoginResponseDTO;
import com.easy_station.sso.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity auth(@RequestBody AuthDTO dto) {
        String token = this.authService.signIn(dto);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
