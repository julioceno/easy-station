package com.easy_station.sso.users.services;

import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindOneUserService {

    @Autowired
    private  UserRepository repository;

    public UserReturnDTO run(String id) {
        User user = this.repository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Usuário de id " + id + " não existe.");
        }

        return new UserReturnDTO(user);
    }
}
