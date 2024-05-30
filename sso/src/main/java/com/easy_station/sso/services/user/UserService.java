package com.easy_station.sso.services.user;

import com.easy_station.sso.dto.user.RegisterDTO;
import com.easy_station.sso.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final CreateUserService createUserService;
    private final FindAllUsersService findAllUsersService;

    public User create(RegisterDTO dto) {
        return createUserService.run(dto);
    }

    public List<User> findAll() {
        return this.findAllUsersService.run();
    }
}
