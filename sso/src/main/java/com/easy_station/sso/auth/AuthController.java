package com.easy_station.sso.auth;

import com.easy_station.sso.auth.dto.RefreshTokenDTO;
import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.auth.dto.AuthDTO;
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
    public ResponseEntity<SignInDTO> auth(@RequestBody AuthDTO dto) {
        SignInDTO signInDTO = this.authService.signIn(dto);
        return ResponseEntity.ok(signInDTO);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<SignInDTO> refreshToken(@RequestBody RefreshTokenDTO dto) {
        SignInDTO signInDTO = this.authService.refreshToken(dto);
        return ResponseEntity.ok(signInDTO);
    }
}
