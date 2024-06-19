package com.easy_station.management.common.grpc.dto;

import br.com.easy_station.sso.Company;
import br.com.easy_station.sso.Role;
import br.com.easy_station.sso.User;
import lombok.Getter;

@Getter
public class UserReturnDTO {
    private String id;
    private String email;
    private UserRoleEnum role;
    private CompanyDTO company;

    public UserReturnDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = mappingEnum(user.getRole());
        this.company = mappingCompany(user.getCompany());
    }

    private UserRoleEnum mappingEnum(Role role) {
        return UserRoleEnum.valueOf(String.valueOf(role));
    }

    private CompanyDTO mappingCompany(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }
}