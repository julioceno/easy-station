package com.easy_station.sso.controllers;

import com.easy_station.sso.dto.AuthUserDTO;
import com.easy_station.sso.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody AuthUserDTO dto) {
        String token = authService.createToken(dto);
        return ResponseEntity.ok().body(token);
    }

}
