package com.easy_station.sso.services.user;

import com.easy_station.sso.domain.user.dto.RegisterDTO;
import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Autowired
    CreateUserService createUserService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }

    public User create(@RequestBody RegisterDTO dto) {
        return createUserService.run(dto);
    }
}
