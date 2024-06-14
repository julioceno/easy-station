package com.easy_station.management.companies;

import com.easy_station.management.companies.database.Management;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.CreateCompanyDTO;
import com.easy_station.management.companies.dto.management.CreateManagementDTO;
import com.easy_station.management.companies.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    ResponseEntity<CompanyDTO> create(@RequestBody CreateCompanyDTO dto) {
        CompanyDTO companyDTO = companyService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(companyDTO.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(companyDTO);
    }

    @GetMapping("/{id}")
    ResponseEntity<CompanyDTO> findOne(@PathVariable String id) {
        CompanyDTO companyDTO = companyService.findOne(id);
        return ResponseEntity.ok(companyDTO);
    }

    @PatchMapping("/{id}")
    ResponseEntity<CompanyDTO> update(
            @PathVariable String id,
            @RequestBody CreateCompanyDTO dto
    ) {
        CompanyDTO companyDTO = companyService.update(id, dto);
        return ResponseEntity.ok(companyDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable String id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/management")
    ResponseEntity<Management> updateManagement(@PathVariable String id, @RequestBody CreateManagementDTO dto) {
        Management management = companyService.upsertManagement(id, dto);
        return ResponseEntity.ok(management);
    }
}
