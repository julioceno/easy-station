package com.easy_station.sso.services.user;

import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllUsersService {

    @Autowired
    private  UserRepository repository;

    public List<User> run() {
        List<User> users = repository.findAll();
        return users;
    }
}
