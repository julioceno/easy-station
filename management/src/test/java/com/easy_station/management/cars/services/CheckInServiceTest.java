package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.companies.dto.CompanyDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CheckInServiceTest {
    @InjectMocks
    CheckInService checkInService;

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

        when(carRepository.save(any(Car.class))).thenReturn(car);
        when(findOneCompanyService.run("companyId")).thenReturn(new CompanyDTO("id", "name", 10.00));
    }

    @Test
    @DisplayName("Should call validateCarsInCourtyardService and invoke run method")
    void test1() {
        checkInService.run(createCarDTO, "companyId");
        verify(validateLimitCarsInCourtyardService).run("courtyardId", "companyId");
    }

    @Test
    @DisplayName("Should call carRepository and invoke findActiveCarByPlateAndCompanyId method")
    void test2() {
        checkInService.run(createCarDTO, "companyId");
       verify(carRepository).findActiveCarByPlateAndCompanyId("plate", "companyId");
    }

    @Test
    @DisplayName("Should throw BadRequestException when car already exists in any courtyard")
    void test3() {
        when(carRepository.findActiveCarByPlateAndCompanyId("plate", "companyId")).thenReturn(Optional.ofNullable(car));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            checkInService.run(createCarDTO, "companyId");
        });

        assertEquals("Já existe um carro com essa placa estacionado em algum dos patios.", exception.getMessage());
    }

    @Test
    @DisplayName("Should call findOneCompanyService and invoke run method")
    void test4() {
        checkInService.run(createCarDTO, "companyId");
        verify(findOneCompanyService).run("companyId");
    }

    @Test
    @DisplayName("Should call carRepository and invoke save method")
    void test5() {
        CarDTO response = checkInService.run(createCarDTO, "companyId");

        verify(carRepository).save(any(Car.class));
        assertThat(response).isInstanceOf(CarDTO.class);
    }
}