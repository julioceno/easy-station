package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.companies.services.FindOneCompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CheckOutServiceTest {
    @InjectMocks
    CheckOutService checkOutService;

    @Mock
    CarRepository carRepository;

    @Mock
    FindOneCompanyService findOneCompanyService;

    @Mock
    ValidateLimitCarsInCourtyardService validateLimitCarsInCourtyardService;

    Car car;
    CarDTO carDTO;
    CreateCarDTO createCarDTO;

    @BeforeEach
    void setUp() {
        car = new Car("id", "ownerName", "plate", 10.0, "courtyardId", null, null);
        carDTO = new CarDTO(car);
        createCarDTO = new CreateCarDTO(car.getOwnerName(), car.getPlate(), car.getCourtyardId());

        when(carRepository.findCarByIdAndCompanyId("id", "companyId")).thenReturn(Optional.ofNullable(car));
        when(carRepository.save(any(Car.class))).thenReturn(car);
    }

    @Test
    @DisplayName("Should call carRepository and invoke findCarByIdAndCompanyId and invoke method")
    void test1() {
        checkOutService.run("id", "companyId");
        verify(carRepository).findCarByIdAndCompanyId("id", "companyId");
    }

    @Test
    @DisplayName("Should throw new NotFoundException when car not exists")
    void test2() {
        when(carRepository.findCarByIdAndCompanyId("id", "companyId")).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            checkOutService.run("id", "companyId");
        });

        assertEquals(format("Veículo de id %s não existe.", "id"),exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BadRequestException when car already updated")
    void test3() {
        car.setUpdatedAt(new Date());
        when(carRepository.findCarByIdAndCompanyId("id", "companyId")).thenReturn(Optional.ofNullable(car));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            checkOutService.run("id", "companyId");
        });

        assertEquals("Carro já saiu do pátio.",exception.getMessage());
    }

    @Test
    @DisplayName("Should call carRepository and invoke save method")
    void test4() {
        checkOutService.run("id", "companyId");

    }
}