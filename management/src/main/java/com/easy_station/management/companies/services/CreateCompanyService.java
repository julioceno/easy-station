package com.easy_station.management.companies.services;

import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.companies.domain.CompanyRepository;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.CreateCompanyDTO;
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
        Company builtCompany = buildCompany(dto);

        logger.info("Saving company...");
        Company companyCreated = companyRepository.save(builtCompany);
        logger.info("Company saved, return company in DTO patten...");

        return new CompanyDTO(companyCreated);
    }

    private Company buildCompany(CreateCompanyDTO dto) {
        logger.info(format("Build company to save with dto %s...", dto.toString()));
        Company createCompany = new Company();
        createCompany.setName(dto.name());
        createCompany.setPriceHour(dto.priceHour());

        logger.info("Company built");
        return createCompany;
    }
}
