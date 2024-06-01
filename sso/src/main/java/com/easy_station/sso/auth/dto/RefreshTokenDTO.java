package com.easy_station.sso.auth.dto;

import lombok.Builder;

@Builder
public record RefreshTokenDTO(String refreshToken) {
}
