package com.easy_station.management.users.dto;

import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.users.domain.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Setter
public class UserDTO {
    private String id;
    private String email;
    private String companyId;
    private String externalId;
    private CompanyDTO company;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.companyId = user.getCompanyId();
    }

}
