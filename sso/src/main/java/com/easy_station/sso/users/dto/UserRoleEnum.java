package com.easy_station.sso.users.dto;

public enum UserRoleEnum {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
