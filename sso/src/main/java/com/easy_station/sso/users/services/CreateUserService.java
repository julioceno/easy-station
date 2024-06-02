package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.BadRequestException;
import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class CreateUserService {
    @Autowired
    UserRepository repository;

    public UserReturnDTO run(CreateUserDTO dto) {
        boolean alreadyExistsUser = this.repository.findByEmail(dto.email()) != null;
        if (alreadyExistsUser) {
            throw new BadRequestException(format("Email %s já esta em uso", dto.email()));
        }

        String encyptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.email(), encyptedPassword, dto.role());
        this.repository.save(newUser);

        return new UserReturnDTO(newUser);
    }
}
