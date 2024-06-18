package com.easy_station.management.cars.services;

import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarsService {
    private final CheckInService checkInService;

    public CarDTO checkIn(CreateCarDTO dto, String companyId) {
        return checkInService.run(dto, companyId);
    }
}
