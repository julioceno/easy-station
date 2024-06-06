package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class FindUserByEmailService {
    @Autowired
    private UserRepository repository;

    public User run(String email) {
        User user = this.repository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new NotFoundException(format("Usuário de email %s não existe.", email));
        }

        return user;
    }
}
