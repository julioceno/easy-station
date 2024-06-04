package com.easy_station.sso.auth.services;

import com.easy_station.sso.auth.dto.SignInDTO;
import com.easy_station.sso.users.dto.AuthDTO;
import com.easy_station.sso.users.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignInService {
    private final AuthenticationManager authenticationManager;
    private final CreateTokenService createTokenService;
    private final CreateRefreshTokenService createRefreshTokenService;

    public SignInDTO run(AuthDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();

        String token = createTokenService.run(user);
        String refreshToken = createRefreshTokenService.run(user.getId());

        return SignInDTO
                .builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
