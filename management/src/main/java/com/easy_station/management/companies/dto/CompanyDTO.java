package com.easy_station.management.companies.dto;

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
}
