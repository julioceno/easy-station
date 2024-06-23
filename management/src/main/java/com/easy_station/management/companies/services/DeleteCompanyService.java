package com.easy_station.management.companies.services;

import com.easy_station.management.companies.domain.CompanyRepository;
import com.easy_station.management.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class DeleteCompanyService {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCompanyService.class.getName());

    @Autowired
    private CompanyRepository companyRepository;

    public void run(String id) {
        verifyIfCompanyExists(id);
        logger.info(format("Deleting company with id %s...", id));
        companyRepository.deleteById(id);
        logger.info("Company deleted");
    }

    public void verifyIfCompanyExists(String id) {
        logger.info(format("Getting company with id %s...", id));
        boolean existsCompany = companyRepository.existsById(id);

        if (!existsCompany) {
            logger.error(format("Company with id %s not found", id));
             throw new NotFoundException(format("Empresa de id %s não existe", id));
        }

        logger.info("Company not exists");
    }
}
