package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.exceptions.NotFoundException;
import com.easy_station.management.infra.http.interceptors.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class FindOneCourtyardService {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class.getName());

    @Autowired
    private CourtyardsRepository courtyardsRepository;

    public CourtyardDTO run(String id, String companyId) {
        Courtyard courtyard = findCourtyardByIdAndCompanyId(id, companyId);

       logger.info("Return courtyard in pattern DTO...");
       return new CourtyardDTO(courtyard.getId(), courtyard.getName(), courtyard.getMaxCars());
    }

    public Courtyard findCourtyardByIdAndCompanyId(String id, String companyId) {
        logger.info(format("Getting courtyard by id %s and company id %s...", id, companyId));
        Courtyard courtyard = courtyardsRepository.findByIdAndCompanyId(id, companyId).orElseThrow(() -> {
            logger.error("Courtyard not found");
            return new NotFoundException("Patio não foi encontrado.");
        });

        logger.info("Courtyard found");
        return courtyard;
    }
}
