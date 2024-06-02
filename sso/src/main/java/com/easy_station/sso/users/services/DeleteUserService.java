package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class DeleteUserService {
    @Autowired
    private UserRepository repository;

    public void run(String id) {
        this.verifyIfUserExists(id);
        repository.deleteById(id);
    }

    public void verifyIfUserExists(String id) {
        boolean userExists = this.repository.existsById(id);
        if (!userExists) {
            throw new NotFoundException(format("Usuário de id %s não existe", id));
        }
    }
}
