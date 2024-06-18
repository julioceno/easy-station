package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.services.FindOneCompanyService;
import com.easy_station.management.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.*;

@Service
public class CheckInService {
    private final Logger logger = LoggerFactory.getLogger(CheckInService.class.getName());

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FindOneCompanyService findOneCompanyService;

    public CarDTO run(CreateCarDTO dto, String companyId) {
        throwIfAlreadyCarActive(dto.plate(), companyId);
        Car carCreated = createCar(dto, companyId);

        logger.info("Return car in DTO pattern...");
        return new CarDTO(carCreated);
    }

    private void throwIfAlreadyCarActive(String plate, String companyId) {
        logger.info(format("Finding cars by plate %s in company %s", plate, companyId));
        Car car = carRepository.findActiveCarByPlateAndCompanyId(plate, companyId).orElse(null);
        if (car != null) {
            logger.error("Already exists car in any courtyard in this company");
            throw new BadRequestException("Já existe um carro com essa placa estacionado em algum dos patios.");
        }

        logger.info("Car not exists in this company");
    }

    private Car createCar(CreateCarDTO createCarDTO, String companyId) {
        Double priceHour = getPriceHour(companyId);

        logger.info("Building car...");
        Car car = new Car();
        car.setOwnerName(createCarDTO.ownerName());
        car.setPlate(createCarDTO.plate());
        car.setHourPrice(priceHour);
        car.setCourtyardId(createCarDTO.courtyardId());

        logger.info("Car built, saving car...");
        Car carSaved = carRepository.save(car);
        logger.info("Car saved");

        return carSaved;
    }

    private Double getPriceHour(String companyId) {
        logger.info("Call findOneCompanyService and invoke run method for get company");
        CompanyDTO company = findOneCompanyService.run(companyId);
        Double priceHour = company.getPriceHour();

        logger.info(format("Price got with value %s", priceHour));
        return priceHour;
    }
}
