package com.easy_station.management.company.services;

import com.easy_station.management.company.database.Company;
import com.easy_station.management.company.database.CompanyRepository;
import com.easy_station.management.company.dto.CompanyDTO;
import com.easy_station.management.company.dto.CreateCompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class CreateCompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CreateCompanyService.class.getName());

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyDTO run(CreateCompanyDTO dto) {
        Company buildedUser = buildUser(dto);

        logger.info("Saving company...");
        Company companyCreated = companyRepository.save(buildedUser);
        logger.info("Company saved, return company...");

        return new CompanyDTO(companyCreated.getId(), companyCreated.getName());
    }

    private Company buildUser(CreateCompanyDTO dto) {
        logger.info(format("Build user to save with dto %s...", dto.toString()));
        Company createCompany = new Company();
        createCompany.setName(dto.name());

        logger.info("User builded");
        return createCompany;
    }
}