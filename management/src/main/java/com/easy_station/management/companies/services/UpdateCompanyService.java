package com.easy_station.management.companies.services;

import com.easy_station.management.companies.database.Company;
import com.easy_station.management.companies.database.CompanyRepository;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.UpdateCompanyDTO;
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

    public CompanyDTO run(String id, UpdateCompanyDTO dto) {
        Company currentCompany = getCompany(id);
        Company companyUpdated = updateCompany(currentCompany, dto);

        logger.info("Return company in pattern dto...");
        return new CompanyDTO(companyUpdated.getId(), companyUpdated.getName(), companyUpdated.getPriceHour());
    }

    private Company updateCompany(Company currentCompany, UpdateCompanyDTO dto) {
        currentCompany.setName(dto.name());
        currentCompany.setPriceHour(dto.priceHour());

        logger.info("Updating company...");
        Company companyUpdated = companyRepository.save(currentCompany);
        logger.info("Company updated");
        return companyUpdated;
    }

    private Company getCompany(String id) {
        logger.info(format("Getting company with id %s...", id));
        Company company = companyRepository.findById(id).orElseThrow(() -> {
            logger.error(format("Company with id %s not found", id));
            return new NotFoundException(format("Empresa de id %s não existe", id));
        });

        logger.info("Company found, return...");
        return company;
    }
}
