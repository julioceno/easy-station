package com.easy_station.management.cars.services;

import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarsService {
    private final CheckInService checkInService;
    private final CheckOutService checkOutService;

    public CarDTO checkIn(CreateCarDTO dto, String companyId) {
        return checkInService.run(dto, companyId);
    }

    public CarDTO checkOut(String id, String companyId) {
        return checkOutService.run(id, companyId);
    }
}
