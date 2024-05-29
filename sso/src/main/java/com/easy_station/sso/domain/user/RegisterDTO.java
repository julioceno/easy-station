package com.easy_station.sso.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
