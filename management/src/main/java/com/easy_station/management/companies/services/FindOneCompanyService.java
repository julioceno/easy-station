package com.easy_station.management.companies.services;

import com.easy_station.management.companies.database.Company;
import com.easy_station.management.companies.database.CompanyRepository;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class FindOneCompanyService {
    private static final Logger logger = LoggerFactory.getLogger(FindOneCompanyService.class.getName());

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyDTO run(String id) {
        logger.info(format("Getting company with id %s...", id));
        Company company = companyRepository.findById(id).orElseThrow(() -> error(id));
        logger.info("Company found, return...");
        return new CompanyDTO(company.getId(), company.getName());
    }

    private NotFoundException error(String id) {
        logger.error(format("Company with id %s not found", id));
        throw new NotFoundException(format("Empresa de id %s não existe", id));
    }
}
