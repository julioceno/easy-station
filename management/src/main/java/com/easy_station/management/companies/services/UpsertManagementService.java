package com.easy_station.management.companies.services;

import com.easy_station.management.companies.database.Management;
import com.easy_station.management.companies.database.ManagementRepository;
import com.easy_station.management.companies.dto.management.CreateManagementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static java.lang.String.*;

@Service
public class UpsertManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UpsertManagementService.class.getName());

    @Autowired
    private ManagementRepository managementRepository;

    public Management run(String companyId, CreateManagementDTO dto) {
        String managementId = getManagementIdIfExists(companyId);

        logger.info("Build management to save...");
        Management management = new Management(companyId, dto.priceHour());
        management.setId(managementId);
        logger.info("Management built");

        logger.info("Updating Management...");
        Management managementUpdated = managementRepository.save(management);
        logger.info("Management updated");

        return managementUpdated;
    }

    private String getManagementIdIfExists(String companyId) {
        logger.info(format("Get management by company with id %s...", companyId));
        Management management = managementRepository.findByCompanyId(companyId).orElse(null);

        if (management != null) {
            logger.info(format("Management found with id %s", companyId));
            return management.getId();
        }

        logger.info("Management not found");
        return null;
    }
}
