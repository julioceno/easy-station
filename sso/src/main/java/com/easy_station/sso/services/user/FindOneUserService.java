package com.easy_station.sso.services.user;

import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOneUserService {

    @Autowired
    private  UserRepository repository;

    public User run(String id) {
        User user = this.repository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Usuário de id " + id + " não existe.");
        }
        return user;
    }
}
