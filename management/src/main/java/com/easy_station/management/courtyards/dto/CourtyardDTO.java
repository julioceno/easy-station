package com.easy_station.management.courtyards.dto;

import com.easy_station.management.courtyards.domain.Courtyard;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourtyardDTO {
    private String id;
    private String name;
    private Integer maxCars;

    public CourtyardDTO(Courtyard courtyard) {
        this.id = courtyard.getId();
        this.name = courtyard.getName();
        this.maxCars = courtyard.getMaxCars();
    }
}
