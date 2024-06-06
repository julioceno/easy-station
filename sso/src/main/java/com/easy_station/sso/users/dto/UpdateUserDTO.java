package com.easy_station.sso.users.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UpdateUserDTO {
    private String email;
    private UserRoleEnum role;
    private CompanyDTO company;
}