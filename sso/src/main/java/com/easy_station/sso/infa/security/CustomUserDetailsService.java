package com.easy_station.sso.infa.security;

import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(username).orElse(null);

        if (user == null) {
            throw new NotFoundException(format("Usuário com email %s não existe.", username));
        }

        return user;
    }
}
