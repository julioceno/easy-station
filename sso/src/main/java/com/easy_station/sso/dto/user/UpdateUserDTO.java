package com.easy_station.sso.dto.user;

import com.easy_station.sso.enums.UserRoleEnum;

public record UpdateUserDTO(String login, UserRoleEnum role) {}