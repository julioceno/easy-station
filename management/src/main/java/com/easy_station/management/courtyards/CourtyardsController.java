package com.easy_station.management.courtyards;

import com.easy_station.management.common.services.GetCompanyIdByTokenService;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import com.easy_station.management.courtyards.dto.UpdateCourtyardDTO;
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

    @GetMapping
    public ResponseEntity<List<CourtyardDTO>> findAll(@RequestAttribute("companyId") String companyId) {
        List<CourtyardDTO> list = courtyardsService.findAll(companyId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtyardDTO> findOne(@RequestAttribute("companyId") String companyId, @PathVariable String id) {
        CourtyardDTO courtyardDTO = courtyardsService.findOne(id, companyId);
        return ResponseEntity.ok(courtyardDTO);
    }

    @PostMapping
    public ResponseEntity<CourtyardDTO> create(@RequestAttribute("companyId") String companyId, @RequestBody CreateCourtyardDTO dto) {
        CourtyardDTO courtyardDTO = courtyardsService.create(dto, companyId);
        return ResponseEntity.ok(courtyardDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtyardDTO> update(@RequestAttribute("companyId") String companyId, @RequestBody UpdateCourtyardDTO dto, @PathVariable String id) {
        CourtyardDTO courtyardDTO = courtyardsService.update(id, dto, companyId);
        return ResponseEntity.ok(courtyardDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestAttribute("companyId") String companyId, @PathVariable String id) {
        courtyardsService.delete(id, companyId);
        return ResponseEntity.noContent().build();
    }

}
