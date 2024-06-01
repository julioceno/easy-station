package com.easy_station.sso.auth;

import com.easy_station.sso.users.dto.AuthDTO;
import com.easy_station.sso.users.dto.LoginResponseDTO;
import com.easy_station.sso.auth.services.AuthService;
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
