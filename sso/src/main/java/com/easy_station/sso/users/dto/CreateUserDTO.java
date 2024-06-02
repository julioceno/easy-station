package com.easy_station.sso.users.dto;

public record CreateUserDTO(String email, String password, UserRoleEnum role) {
}
