package com.easy_station.sso.services.user;

import com.easy_station.sso.dto.user.CreateUserDTO;
import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    @Autowired
    UserRepository repository;

    public User run(CreateUserDTO dto) {
        boolean alreadyExistsUser = this.repository.findByLogin(dto.login()) != null;
        if (alreadyExistsUser) {
            return null;
        }

        String encyptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.login(), encyptedPassword, dto.role());
        this.repository.save(newUser);

        return newUser;
    }
}
