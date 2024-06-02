package com.easy_station.sso.users.dto;

import com.easy_station.sso.users.domain.User;
import lombok.Getter;

@Getter
public class UserReturnDTO {
    private String id;
    private String email;
    private UserRoleEnum role;

    public UserReturnDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
