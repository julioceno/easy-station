package com.easy_station.management.courtyards;

import com.easy_station.management.common.services.GetCompanyIdByTokenService;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import com.easy_station.management.courtyards.services.CourtyardsService;
import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courtyards")
public class CourtyardsController {
    @Autowired
    CourtyardsService courtyardsService;

    @Autowired
    GetCompanyIdByTokenService getCompanyIdByTokenService;

    // TODO: Subsituir as respostas para ResponseEntity

    @GetMapping
    public List<CourtyardDTO> findAll(@RequestAttribute("companyId") String companyId) {
        return courtyardsService.findAll(companyId);
    }

    @GetMapping("/{id}")
    public CourtyardDTO findOne(@RequestAttribute("companyId") String companyId, @PathVariable String id) {
        return courtyardsService.findOne(id, companyId);
    }

    @PostMapping
    public CourtyardDTO create(@RequestAttribute("companyId") String companyId, @RequestBody CreateCourtyardDTO dto) {
        return courtyardsService.create(dto, companyId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestAttribute("companyId") String companyId, @PathVariable String id) {
        courtyardsService.delete(id, companyId);
        return ResponseEntity.noContent().build();
    }

}
