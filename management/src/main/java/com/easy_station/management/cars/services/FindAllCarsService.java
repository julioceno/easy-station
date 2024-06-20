package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.*;

@Service
public class FindAllCarsService {
    private final Logger logger = LoggerFactory.getLogger(FindAllCarsService.class);

    @Autowired
    private CarRepository carRepository;

    public List<CarDTO> run(String companyId) {
        List<Car> cars = getCars(companyId);

        logger.info("List to pattern dto...");
        List<CarDTO> carsDTO = cars.stream().map(CarDTO::new).collect(Collectors.toList());
        logger.info("Returning list...");

        return carsDTO;
    }

    private List<Car> getCars(String companyId) {
        logger.info("getting cars by company");
        List<Car> cars = carRepository.findAllCarsByCompanyId(companyId);
        logger.info(format("%s cars got", cars.size()));
        return cars;
    }
}
