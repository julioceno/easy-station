package com.easy_station.sso.users.services;

import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAllUsersService {
    @Autowired
    private  UserRepository repository;

    public List<UserReturnDTO> run() {
        List<User> users = repository.findAll();

        return users.stream()
                .map(UserReturnDTO::new)
                .collect(Collectors.toList());
    }
}
