package com.easy_station.management.users.dto;

import com.easy_station.management.companies.dto.CompanyDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Setter
public class UserDTO {
    private String id;
    private String name;
    private String companyId;
    private String externalId;
    private CompanyDTO company;

    public UserDTO(String id, String name, String companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

}
