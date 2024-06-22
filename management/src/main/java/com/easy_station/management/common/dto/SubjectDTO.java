package com.easy_station.management.common.dto;

import com.easy_station.management.common.enums.UserRoleEnum;

public record SubjectDTO(String email, UserRoleEnum role) {
}
