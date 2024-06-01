package com.easy_station.sso.users.services;

import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    @Autowired
    UserRepository repository;

    public UserReturnDTO run(CreateUserDTO dto) {
        boolean alreadyExistsUser = this.repository.findByLogin(dto.login()) != null;
        if (alreadyExistsUser) {
            return null;
        }

        String encyptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.login(), encyptedPassword, dto.role());
        this.repository.save(newUser);

        return new UserReturnDTO(newUser);
    }
}
