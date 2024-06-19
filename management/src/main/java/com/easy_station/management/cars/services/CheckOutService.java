package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.String.*;

@Service
public class CheckOutService {
    private final Logger logger = LoggerFactory.getLogger(CheckOutService.class.getName());

    @Autowired
    private CarRepository carRepository;

    public CarDTO run(String id, String companyId) {
        Car currentCar = getCar(id, companyId);
        throwIfCarrorAlreadyOut(currentCar);

        Car updatedCar = updateCar(currentCar);

        logger.info("Return car in DTO pattern...");
        return new CarDTO(updatedCar);
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

    private void throwIfCarrorAlreadyOut(Car currentCar) {
        if (currentCar.getUpdatedAt() != null) {
            logger.error("Car has already left the courtyard");
            throw new BadRequestException("Carro já saiu do pátio.");
        }
    }

    private Car updateCar(Car currentCar) {
        currentCar.setUpdatedAt(new Date());

        logger.info("Updating car...");
        Car updatedCar = carRepository.save(currentCar);
        logger.info("Car updated");

        return updatedCar;
    }
}
