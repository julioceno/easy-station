package com.easy_station.management.company.services;

import com.easy_station.management.company.database.Company;
import com.easy_station.management.company.database.CompanyRepository;
import com.easy_station.management.company.dto.CompanyDTO;
import com.easy_station.management.company.dto.CreateCompanyDTO;
import com.easy_station.management.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UpdateCompanyService {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCompanyService.class.getName());

    @Autowired
    private CompanyRepository companyRepository;

    // TODO: alterar o dto ou renomear
    public CompanyDTO run(String id, CreateCompanyDTO dto) {
        Company currentCompany = getCompany(id);
        currentCompany.setName(dto.name());

        Company companyUpdated = companyRepository.save(currentCompany);
        return new CompanyDTO(companyUpdated.getId(), companyUpdated.getName());
    }

    public Company getCompany(String id) {
        logger.info(format("Getting company with id %s...", id));
        Company company = companyRepository.findById(id).orElseThrow(() -> {
            logger.error(format("Company with id %s not found", id));
            return new NotFoundException(format("Empresa de id %s não existe", id));
        });

        logger.info("Company found, return...");
        return company;
    }
}
