package com.easy_station.management.companies.services;

import com.easy_station.management.companies.database.Management;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.CreateCompanyDTO;
import com.easy_station.management.companies.dto.management.CreateManagementDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CreateCompanyService createCompanyService;
    private final FindOneCompanyService findOneCompanyService;
    private final UpdateCompanyService updateCompanyService;
    private final DeleteCompanyService deleteCompanyService;
    private final UpsertManagementService upsertManagementService;

    public CompanyDTO findOne(String id) {
        return findOneCompanyService.run(id);
    }

    public CompanyDTO create(CreateCompanyDTO dto) {
        return createCompanyService.run(dto);
    }

    public CompanyDTO update(String id, CreateCompanyDTO dto) {
        return this.updateCompanyService.run(id, dto);
    }

    public Management upsertManagement(String companyId, CreateManagementDTO dto) {
        return this.upsertManagementService.run(companyId, dto);
    }

    public void delete(String id) {
        this.deleteCompanyService.run(id);
    }
}
