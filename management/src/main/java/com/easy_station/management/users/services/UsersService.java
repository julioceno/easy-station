package com.easy_station.management.users.services;

import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {
    private final CreateUserService createUserService;

    public UserDTO create(CreateUserDTO createUserDTO) {
        return createUserService.run(createUserDTO);
    }
}
