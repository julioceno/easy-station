package com.easy_station.sso.users.services;

import com.easy_station.sso.users.dto.UpdateOwnUserDTO;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOwnUserService {
    @Autowired
    private UpdateUserService updateUserService;

    public UserReturnDTO run(String id, UpdateOwnUserDTO dto) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(dto.email(), null, null);
        return updateUserService.run(id, updateUserDTO);
    }
}
