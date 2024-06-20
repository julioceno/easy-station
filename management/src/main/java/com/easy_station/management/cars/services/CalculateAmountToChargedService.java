package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CalculatemountToChargedDTO;
import com.easy_station.management.cars.dto.CarDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Date;
import static java.lang.String.*;

@Service
public class CalculateAmountToChargedService {
    private final Logger logger = LoggerFactory.getLogger(CalculateAmountToChargedService.class.getName());
    private final DecimalFormat df = new DecimalFormat("#.00");


    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FindOneCarService findOneCarService;

    public CalculatemountToChargedDTO run(String id, String companyId) {
        CarDTO car = findOneCarService.run(id, companyId);

        double primitiveValue = calculateBeetwenMinutes(car);
        String value = df.format(primitiveValue);

        logger.info("Return data in dto pattern...");
        return new CalculatemountToChargedDTO(value);
    }

    private Date getCurrentDate() {
        return new Date();
    }

    private double calculateBeetwenMinutes(CarDTO car) {
        logger.info("Calculating value...");
        double valuePerMinute = car.getHourPrice() / 60;
        Duration betweened = Duration.between(car.getCheckIn().toInstant(), getCurrentDate().toInstant());
        long minutes = betweened.toMinutes();
        double value = minutes * valuePerMinute;
        logger.info(format("Value caulated %s", value));

        return value;
    }
}
