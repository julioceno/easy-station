package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static java.lang.String.*;

@Service
public class DeleteCourtyardService {
    private final Logger logger = LoggerFactory.getLogger(DeleteCourtyardService.class);

    @Autowired
    private CourtyardsRepository courtyardsRepository;

    public void run(String id, String companyId) {
        throwIfCourtyardNotExists(id, companyId);
        deleteCourtyard(id, companyId);
    }

    private void throwIfCourtyardNotExists(String id, String companyId) {
        logger.info(format("Getting courtyard by id %s and companyId %s", id, companyId));
        courtyardsRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> {
            logger.error("Courtyard not exists, throw error...");
            return new NotFoundException("Pátio não existe");
        });

        logger.info("Courtyard found");
    }

    private void deleteCourtyard(String id, String companyId) {
        logger.info("Deleting courtyard...");
        courtyardsRepository.deleteByIdAndCompanyId(id, companyId);
        logger.info("Courtyard deleted");
    }
}
