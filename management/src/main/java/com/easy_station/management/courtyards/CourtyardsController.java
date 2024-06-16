package com.easy_station.management.courtyards;

import com.easy_station.management.common.services.GetCompanyIdByTokenService;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import com.easy_station.management.courtyards.services.CourtyardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courtyards")
public class CourtyardsController {
    @Autowired
    CourtyardsService courtyardsService;

    @Autowired
    GetCompanyIdByTokenService getCompanyIdByTokenService;

    @GetMapping("/{id}")
    public CourtyardDTO findOne(@RequestHeader("Authorization") String bearerToken, @PathVariable String id) {
        // TODO: criar filter que já vai fazer a busca do companyId e pegar depois pelo decorator
        String companyId = getCompanyIdByTokenService.run(bearerToken);
        return courtyardsService.findOne(id, companyId);
    }

    @PostMapping
    public CourtyardDTO create(@RequestHeader("Authorization") String bearerToken, @RequestBody CreateCourtyardDTO dto) {
        // TODO: criar filter que já vai fazer a busca do companyId e pegar depois pelo decorator
        String companyId = getCompanyIdByTokenService.run(bearerToken);
        return courtyardsService.create(dto, companyId);
    }

}
