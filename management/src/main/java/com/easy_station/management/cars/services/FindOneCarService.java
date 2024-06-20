package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class FindOneCarService {
    private final Logger logger = LoggerFactory.getLogger(FindOneCarService.class.getName());

    @Autowired
    private CarRepository carRepository;

    public CarDTO run(String id, String companyId) {
        Car currentCar = getCar(id, companyId);
        logger.info("Return car in dto pattern...");
        return new CarDTO(currentCar);
    }

    private Car getCar(String id, String companyId) {
        logger.info(format("Finding cars by id %s in company %s", id, companyId));
        Car car = carRepository.findCarByIdAndCompanyId(id, companyId).orElseThrow(() -> {
            logger.error("Car not found");
            return new NotFoundException("Veículo não encontrado.");
        });

        logger.info("Car found");
        return car;
    }
}
