package com.easy_station.management.cars.services;

import com.easy_station.management.cars.dto.CreateCarDTO;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarsServiceTest {
    @InjectMocks
    CarsService carsService;

    @Mock
    CheckInService checkInService;

    @Mock
    CheckOutService checkOutService;

    @Mock
    FindAllCarsService findAllCarsService;

    @Mock
    FindOneCarService findOneCarService;

    @Mock
    CalculateAmountToChargedService calculateAmountToChargedService;

    @Test
    @DisplayName("Should call checkInService and invoke run method")
    void test1() {
        CreateCarDTO createCarDTO = new CreateCarDTO("ownerName", "plate", "courtyardId");
        carsService.checkIn(createCarDTO, "companyId");
        verify(checkInService).run(createCarDTO, "companyId");
    }

    @Test
    @DisplayName("Should call checkOutService and invoke run method")
    void test2() {
        carsService.checkOut("id", "companyId");
        verify(checkOutService).run("id", "companyId");
    }

    @Test
    @DisplayName("Should call findAllCarsService and invoke run method")
    void test3() {
        carsService.findAll("companyId");
        verify(findAllCarsService).run("companyId");
    }

    @Test
    @DisplayName("Should call findOneCarService and invoke run method")
    void test4() {
        carsService.findOne("id", "companyId");
        verify(findOneCarService).run("id", "companyId");
    }

    @Test
    @DisplayName("Should call calculateAmountToChargedService and invoke run method")
    void test5() {
        carsService.calculateAmountToCharged("id", "companyId");
        verify(calculateAmountToChargedService).run("id", "companyId");
    }
}