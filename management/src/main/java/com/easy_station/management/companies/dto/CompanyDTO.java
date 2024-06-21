package com.easy_station.management.companies.dto;

import com.easy_station.management.companies.domain.Company;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CompanyDTO {
    private String id;
    private String name;
    private Double priceHour;


    public CompanyDTO(Company companyCreated) {
        this.id = companyCreated.getId();
        this.name = companyCreated.getName();
        this.priceHour = companyCreated.getPriceHour();
    }
}
