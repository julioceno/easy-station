package com.easy_station.sso.users.dto;

public record CreateUserDTO(String login, String password, UserRoleEnum role) {
}
