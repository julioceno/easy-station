package com.easy_station.sso.domain.user.dto;

import com.easy_station.sso.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
