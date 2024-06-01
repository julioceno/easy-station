package com.easy_station.sso.users.dto;

import com.easy_station.sso.users.domain.User;
import lombok.Getter;

@Getter
public class UserReturnDTO {
    private String login;
    private UserRoleEnum role;

    public UserReturnDTO(User user) {
        this.login = user.getLogin();
        this.role = user.getRole();
    }
}
