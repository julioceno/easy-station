package com.easy_station.sso.dto;

import java.io.Serializable;

public class AuthUserDTO implements Serializable {
    private String email;
    private String password;

    public AuthUserDTO() {}

    public AuthUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
