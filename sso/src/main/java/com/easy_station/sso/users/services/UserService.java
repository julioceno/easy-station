package com.easy_station.sso.users.services;

import com.easy_station.sso.users.dto.CreateUserDTO;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdatePasswordDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final CreateUserService createUserService;
    private final FindAllUsersService findAllUsersService;
    private final FindOneUserService findOneUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;
    private final UpdatePasswordService updatePasswordService;

    public UserReturnDTO create(CreateUserDTO dto) {
        return createUserService.run(dto);
    }

    public List<UserReturnDTO> findAll() {
        return findAllUsersService.run();
    }

    public UserReturnDTO findOne(String id) {
        return findOneUserService.run(id);
    }

    public UserReturnDTO update(String id, UpdateUserDTO dto) {
        return updateUserService.run(id, dto);
    }

    public UserReturnDTO updatePassword(String email, UpdatePasswordDTO dto) {
        return updatePasswordService.run(email, dto);
    }

    public void delete(String id) {
        deleteUserService.run(id);
    }
}
