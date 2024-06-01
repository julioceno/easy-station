package com.easy_station.sso.dto.user;

import com.easy_station.sso.enums.UserRoleEnum;

public record CreateUserDTO(String login, String password, UserRoleEnum role) {
}
