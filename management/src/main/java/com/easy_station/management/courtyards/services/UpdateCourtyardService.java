package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.UpdateCourtyardDTO;
import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UpdateCourtyardService {
    private final Logger logger =  LoggerFactory.getLogger(UpdateCourtyardService.class.getName());

    @Autowired
    private CourtyardsRepository courtyardsRepository;

    public CourtyardDTO run(String id, UpdateCourtyardDTO dto, String companyId) {
        Courtyard currendCourtyard = getCourtyardAndThrowIfNotExists(id, companyId);
        verifyIfNameAlreadyUsedByOtherCourtyard(id, dto.name(), companyId);
        Courtyard courtyardUpdated = updateCourtyard(currendCourtyard, dto);

        logger.info("Return courtyard in patten dto...");
        return new CourtyardDTO(courtyardUpdated);
    }

    private Courtyard getCourtyardAndThrowIfNotExists(String id, String companyUd) {
        logger.info(format("Finding company by id %s and companyId %s...", id, companyUd));
        Courtyard courtyard = courtyardsRepository.findByIdAndCompanyId(id, companyUd).orElseThrow(() -> {
            logger.error(format("Courtyard with id %s not exists", id));
            return new NotFoundException(format("Pátio com o id %s não existe", id));
        });

        logger.info("Courtyard found, return...");
        return courtyard;
    }

    private void verifyIfNameAlreadyUsedByOtherCourtyard(String id, String name, String companyId) {
        logger.info(format("Finding courtyard by name %s and companyId %s nut different id %s", name, companyId, id));
        Courtyard courtyard = courtyardsRepository.findByNameAndCompanyIdAndNotId(name, companyId, id).orElse(null);

        if (courtyard != null) {
            logger.error(format("Name %s is already in use", name));
            throw new BadRequestException(format("Nome %s já esta em uso", name));
        }

        logger.info("Not exists other courtyard with same name");
    }

    private Courtyard updateCourtyard(Courtyard currendCourtyard, UpdateCourtyardDTO dto) {
        logger.info("Building courtyard to update...");
        currendCourtyard.setName(dto.name());
        currendCourtyard.setMaxCars(dto.maxCars());
        logger.info("Courtyard built, updating...");

        Courtyard courtyardSaved = courtyardsRepository.save(currendCourtyard);
        logger.info("Courtyard updated");
        return courtyardSaved;
    }
}
