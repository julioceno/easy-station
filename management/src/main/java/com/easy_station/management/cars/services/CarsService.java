package com.easy_station.management.cars.services;

import com.easy_station.management.cars.dto.CalculatemountToChargedDTO;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarsService {
    private final CheckInService checkInService;
    private final CheckOutService checkOutService;
    private final FindAllCarsService findAllCarsService;
    private final FindOneCarService findOneCarService;
    private final CalculateAmountToChargedService calculateAmountToChargedService;

    public CarDTO checkIn(CreateCarDTO dto, String companyId) {
        return checkInService.run(dto, companyId);
    }

    public CarDTO checkOut(String id, String companyId) {
        return checkOutService.run(id, companyId);
    }

    public List<CarDTO> findAll(String companyId) {
        return findAllCarsService.run(companyId);
    }

    public CarDTO findOne(String id, String companyId) {
        return findOneCarService.run(id, companyId);
    }

    public CalculatemountToChargedDTO calculateAmountToCharged(String id, String companyId) {
        return calculateAmountToChargedService.run(id, companyId);
    }
}
