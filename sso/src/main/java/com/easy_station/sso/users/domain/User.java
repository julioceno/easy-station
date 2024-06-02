package com.easy_station.sso.users.domain;

import com.easy_station.sso.users.dto.UserRoleEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public @Data  class User implements UserDetails {
    @Id
    private String id;
    private String email;
    private String password;
    private UserRoleEnum role;

    private CompanyDTO company;

    public User(String id, String email, String password, UserRoleEnum role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password, UserRoleEnum role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        boolean isAdmin = role == UserRoleEnum.ADMIN;
        if (isAdmin) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
