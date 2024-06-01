package com.easy_station.sso.users.services;

import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdateUserDTO;
import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.dto.UserReturnDTO;
import com.easy_station.sso.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService {
    @Autowired
    UserRepository repository;

    public UserReturnDTO run(String id, UpdateUserDTO dto) {
        User currentUser = this.getUser(id);
        User userUpdated = updateUser(currentUser, dto);

        return new UserReturnDTO(userUpdated);
    }

    private User getUser(String id) {
        User user = this.repository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Usuário de id " + id + " não existe.");
        }
        return user;
    }

    private User updateUser(User user, UpdateUserDTO dto) {
        User userUpdated = Utils.copyNonNullProperties(dto, user);
        repository.save(userUpdated);
        return user;
    }
}
