package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourtyardsService {
    private final FindOneCourtyardService findOneCourtyardService;
    private final CreateCourtyardService createCourtyardService;

    public CourtyardDTO findOne(String id, String companyId) {
        return findOneCourtyardService.run(id, companyId);
    }

    public CourtyardDTO create(CreateCourtyardDTO dto, String companyId) {
        return createCourtyardService.run(dto, companyId);
    }
}
