package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.*;

@Service
public class FindAllCourtyardsService {
    private final Logger logger = LoggerFactory.getLogger(FindAllCourtyardsService.class);

    @Autowired
    private CourtyardsRepository courtyardsRepository;

    public List<CourtyardDTO> run(String companyId) {
        List<Courtyard> courtyards = getCourtyards(companyId);

        logger.info("Return courtyards, return courtyards by pattern DTO...");
        return courtyards.stream()
                .map(CourtyardDTO::new)
                .collect(Collectors.toList());
    }

    private List<Courtyard> getCourtyards(String companyId) {
        logger.info(format("Getting courtyards by company %s", companyId));
        List<Courtyard> courtyards = courtyardsRepository.findAllByCompanyId(companyId);
        logger.info(format("%s courtyards got", courtyards.size()));
        return courtyards;
    }
}
