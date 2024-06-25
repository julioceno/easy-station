package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.services.FindOneCourtyardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.lang.String.*;

@Service
public class ValidateLimitCarsInCourtyardService {
    private final Logger logger = LoggerFactory.getLogger(ValidateLimitCarsInCourtyardService.class.getName());

    @Autowired
    private FindOneCourtyardService findOneCourtyardService;

    @Autowired
    private CarRepository carRepository;

    public void run(String courtyardId, String companyId) {
        CourtyardDTO courtyardDTO = findOneCourtyardService.run(courtyardId, companyId);
        List<Car> cars = getCars(courtyardId, companyId);

        if (cars.size() >= courtyardDTO.getMaxCars()) {
            logger.error(format("Courtyard has %s cars and limit max is %s", cars.size(), courtyardDTO.getMaxCars()));
            throw new BadRequestException("Não é possível seguir com a operação pois o patio em questão ja está cheio.");
        }

        logger.info("No has errors");
    }

    private List<Car> getCars(String courtyardId, String companyId) {
        logger.info(format("Getting cars by courtyardId %s and companyId %s", courtyardId, companyId));
        List<Car> cars = carRepository.findAllActiveCarsByCourtyardIdAndCompanyId(courtyardId, companyId);
        logger.info(format("%s cars found", cars.size()));
        return cars;
    }
}
