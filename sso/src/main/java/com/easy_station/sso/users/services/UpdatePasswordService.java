package com.easy_station.sso.users.services;

import com.easy_station.sso.exceptions.NotFoundException;
import com.easy_station.sso.exceptions.UnauthorizedException;
import com.easy_station.sso.users.UserRepository;
import com.easy_station.sso.users.domain.User;
import com.easy_station.sso.users.dto.UpdatePasswordDTO;
import com.easy_station.sso.users.dto.UserReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordService {
    @Autowired
    FindUserByEmailService findUserByEmailService;

    @Autowired
    private UserRepository repository;

    public UserReturnDTO run(String email, UpdatePasswordDTO dto) {
        User currentUser = this.findUserByEmailService.run(email);
        this.validateCurrentPassword(currentUser, dto.oldPassword());
        User user = updateUser(currentUser, dto.newPassword());

        return new UserReturnDTO(user);
    }

    private void validateCurrentPassword(User currentUser, String oldPassword) {
        boolean equalsPassword = BCrypt.checkpw(oldPassword, currentUser.getPassword());

        if (!equalsPassword) {
            throw new UnauthorizedException();
        }
    }

    private User updateUser(User user, String newPassword) {
        String newPasswordEncoded = new BCryptPasswordEncoder()
                .encode(newPassword);

        user.setPassword(newPasswordEncoded);
        this.repository.save(user);

        return user;
    }
}
