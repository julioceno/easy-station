package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.database.Courtyard;
import com.easy_station.management.courtyards.database.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import com.easy_station.management.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import static java.lang.String.*;

@Service
public class CreateCourtyardService {
    private final Logger logger =  LoggerFactory.getLogger(CreateCourtyardService.class.getName());

    @Autowired
    private CourtyardsRepository courtyardsRepository;

    public CourtyardDTO run(CreateCourtyardDTO dto, String companyId) {
        throwIfCourtyardAlreadyExists(dto.name(), companyId);
        Courtyard courtyardCreated = crateCourtyard(dto, companyId);

        logger.info("Return courtyard in patten dto...");
        return new CourtyardDTO(courtyardCreated.getId(), courtyardCreated.getName(), courtyardCreated.getMaxCars());
    }

    private void throwIfCourtyardAlreadyExists(String name, String companyUd) {
        logger.info(format("Finding company by name %s and companyId %s...", name, companyUd));
        courtyardsRepository.findByNameAndCompanyId(name, companyUd).orElseThrow(() -> {
            throw new BadRequestException("Pátio já existe para essa empresa.");
        });

        logger.info("Courtyard not found");
    }

    private Courtyard crateCourtyard(CreateCourtyardDTO dto, String companyId) {
        logger.info("Building courtyard to create...");
        Courtyard courtyardToCreate = new Courtyard();
        courtyardToCreate.setName(dto.name());
        courtyardToCreate.setMaxCars(dto.maxCards());
        courtyardToCreate.setCompanyId(companyId);
        logger.info("Courtyard builded, save database...");

        Courtyard courtyardSaved = courtyardsRepository.save(courtyardToCreate);
        logger.info("Courtyard saved");
        return courtyardSaved;
    }
}