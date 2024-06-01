package com.easy_station.sso.services.user;

import com.easy_station.sso.dto.user.CreateUserDTO;
import com.easy_station.sso.domain.user.User;
import com.easy_station.sso.dto.user.UpdateUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final CreateUserService createUserService;
    private final FindAllUsersService findAllUsersService;
    private final UpdateUserService updateUserService;

    public User create(CreateUserDTO dto) {
        return createUserService.run(dto);
    }

    public List<User> findAll() {
        return findAllUsersService.run();
    }

    public User update(String id, UpdateUserDTO dto) {
        return updateUserService.run(id, dto);
    }
}
