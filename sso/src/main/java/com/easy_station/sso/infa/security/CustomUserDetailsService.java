package com.easy_station.sso.infa.security;

import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByLogin(username);

        if (user == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        return user;
    }
}
