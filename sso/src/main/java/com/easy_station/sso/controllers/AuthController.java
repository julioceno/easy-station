package com.easy_station.sso.controllers;

import com.easy_station.sso.domain.user.AuthDTO;
import com.easy_station.sso.domain.user.LoginResponseDTO;
import com.easy_station.sso.domain.user.RegisterDTO;
import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.dto.AuthUserDTO;
import com.easy_station.sso.repositories.UserRepository;
import com.easy_station.sso.services.AuthService;
import com.easy_station.sso.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity auth(@RequestBody AuthDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO dto) {
        Boolean alreadyExistsUser = this.repository.findByLogin(dto.login()) != null;
        if (alreadyExistsUser) {
            return ResponseEntity.badRequest().build();
        }

        String encyptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.login(), encyptedPassword, dto.role());
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
