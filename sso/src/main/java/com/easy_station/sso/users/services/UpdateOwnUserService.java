package com.easy_station.sso.users.services;

import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdateOwnUserDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOwnUserService {
    @Autowired
    UpdateUserService updateUserService;

    @Autowired
    private FindUserByEmailService findUserByEmailService;

    public UserReturnDTO run(String email, UpdateOwnUserDTO dto) {
        User user = findUserByEmailService.run(email);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(dto.email(), null, null);
        return updateUserService.run(user.getId(), updateUserDTO);
    }
}
