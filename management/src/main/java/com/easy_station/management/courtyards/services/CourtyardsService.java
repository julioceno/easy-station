package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourtyardsService {
    private final FindOneCourtyardService findOneCourtyardService;
    private final CreateCourtyardService createCourtyardService;
    private final FindAllCourtyardsService findAllCourtyardsService;
    private final DeleteCourtyardService deleteCourtyardService;

    public CourtyardDTO findOne(String id, String companyId) {
        return findOneCourtyardService.run(id, companyId);
    }

    public CourtyardDTO create(CreateCourtyardDTO dto, String companyId) {
        return createCourtyardService.run(dto, companyId);
    }

    public List<CourtyardDTO> findAll(String companyId) {
        return findAllCourtyardsService.run(companyId);
    }

    public void delete(String id, String companyId) {
        deleteCourtyardService.run(id, companyId);
    }
}
